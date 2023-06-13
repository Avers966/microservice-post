package ru.skillbox.diplom.group35.microservice.post.service.post;

import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.between;
import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.getBaseSpecification;
import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.in;
import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.notIn;

import java.time.ZonedDateTime;
import java.util.*;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.library.core.dto.streaming.EventNotificationDto;
import ru.skillbox.diplom.group35.library.core.utils.SecurityUtil;
import ru.skillbox.diplom.group35.microservice.friend.feignclient.FriendFeignClient;
import ru.skillbox.diplom.group35.microservice.notification.dto.NotificationType;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostSearchDto;
import ru.skillbox.diplom.group35.microservice.post.mapper.post.PostMapper;
import ru.skillbox.diplom.group35.microservice.post.model.like.Like;
import ru.skillbox.diplom.group35.microservice.post.model.like.LikeType;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;
import ru.skillbox.diplom.group35.microservice.post.model.post.PostType;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post_;
import ru.skillbox.diplom.group35.microservice.post.model.post.Tag;
import ru.skillbox.diplom.group35.microservice.post.model.post.Tag_;
import ru.skillbox.diplom.group35.microservice.post.repository.like.LikeRepository;
import ru.skillbox.diplom.group35.microservice.post.repository.post.PostRepository;

/**
 * PostService
 *
 * @author Marat Safagareev
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final TagService tagService;

  private final LikeRepository likeRepository;
  private final PostMapper postMapper;
  private final FriendFeignClient friendFeignClient;

  private final SecurityUtil securityUtil;
  private final KafkaTemplate<String, EventNotificationDto> kafkaTemplate;
  public static Specification<Post> getSpecByAllFields(PostSearchDto searchDto) {
    return getBaseSpecification(searchDto)
        .and(in(Post_.id, searchDto.getIds(), true))
        .and(in(Post_.authorId, searchDto.getAccountIds(), true))
        .and(notIn(Post_.authorId, searchDto.getBlockedIds(), true))
        .and(between(Post_.time,
            searchDto.getDateFrom() == null ? null : searchDto.getDateFrom(),
            searchDto.getDateFrom() == null ? null : searchDto.getDateTo(), true))
        .and(containsTags(searchDto.getTags()));
  }

  private static Specification<Post> containsTags(Set<String> tags) {
    return (root, query, builder) -> {
      if (tags == null || tags.isEmpty()) {
        return builder.conjunction();
      }
      Path<UUID> ids = root.get(Post_.ID);
      Join<Post, Tag> postsTag = root.join(Post_.tags);
      return query.multiselect(postsTag)
              .where(builder.in(postsTag.get(Tag_.NAME)).value(tags))
              .groupBy(ids)
              .having(builder.equal(builder.count(ids), tags.size()))
              .getRestriction();
    };
  }

  public Page<PostDto> getAll(PostSearchDto searchDto, Pageable pageable) {

    ResponseEntity<List<UUID>> responseAboutFriends = friendFeignClient.getFriendId();
    ResponseEntity<List<UUID>> responseAboutBlocked = friendFeignClient.getBlockFriendId();

    if (responseAboutFriends.getStatusCode().equals(HttpStatus.OK) &&
        responseAboutBlocked.getStatusCode().equals(HttpStatus.OK)) {

      List<UUID> listFriends = responseAboutFriends.getBody();
      listFriends.add(securityUtil.getAccountDetails().getId());
      List<UUID> listBlocked = responseAboutBlocked.getBody();

      if (!listBlocked.isEmpty()) {
        searchDto.setBlockedIds(listBlocked);
      }
      if (searchDto.getWithFriends() != null) {
        searchDto.setAccountIds(listFriends);
      }
    }

    if (searchDto.getDateTo() == null) {
      searchDto.setDateTo(ZonedDateTime.now());
    }
    Page<Post> result = postRepository.findAll(getSpecByAllFields(searchDto), pageable);
    return new PageImpl<>(result.map(this::getPostDto).toList(), pageable, result.getTotalElements());
  }

  public PostDto getPostDto(Post post) {
    PostDto postDto = postMapper.toPostDto(post);
    postDto.setReactions(new HashSet<>(likeRepository.findReactions(post.getId())));
    postDto.setTags(tagService.getTags(post.getTags()));
    Like like = likeRepository.findByTypeAndItemIdAndAuthorId(
                              LikeType.POST, post.getId(), securityUtil.getAccountDetails().getId()).orElse(null);
    postDto.setMyLike(like != null && !like.getIsDeleted());
    postDto.setMyReaction(like != null ? like.getReactionType() : null);
    return postDto;
  }

  public PostDto getById(UUID id) {
    return postMapper.toPostDto(postRepository.getById(id));
  }

  public PostDto create(PostDto postDto) {

    if (postDto.getAuthorId() == null) {
      postDto.setAuthorId(securityUtil.getAccountDetails().getId()); //id from token
    }
    postDto.setTime(ZonedDateTime.now());
    postDto.setType(postDto.getPublishDate() != null ? PostType.QUEUED : PostType.POSTED);
    Post post = postMapper.toPost(postDto);
    post.setTags(tagService.saveTags(post));
    createAndSendNotification(post, NotificationType.POST);
    return postMapper.toPostDto(postRepository.save(post));
  }

  public void createAndSendNotification(Post post, NotificationType type) {
    EventNotificationDto notification = new EventNotificationDto();
    notification.setAuthorId(post.getAuthorId());
    notification.setReceiverId(null);
    notification.setNotificationType(String.valueOf(type));
    notification.setContent(post.getTitle());
    kafkaTemplate.send("event", "event", notification);
  }

  public PostDto update(PostDto postDto) {
    Post post = postRepository.getById(postDto.getId());
    post = postMapper.updatePostFromDto(postDto, post);
    tagService.saveTags(post);
    return postMapper.toPostDto(post);
  }

  public void deleteById(UUID id) {
    postRepository.deleteById(id);
  }
}
