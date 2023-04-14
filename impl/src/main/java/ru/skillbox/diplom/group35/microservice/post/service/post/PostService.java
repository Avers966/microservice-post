package ru.skillbox.diplom.group35.microservice.post.service.post;

import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.between;
import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.getBaseSpecification;
import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.in;
import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.notIn;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.criteria.Join;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostSearchDto;
import ru.skillbox.diplom.group35.microservice.post.mapper.post.PostMapper;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;
import ru.skillbox.diplom.group35.microservice.post.model.post.PostType;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post_;
import ru.skillbox.diplom.group35.microservice.post.model.post.Tag;
import ru.skillbox.diplom.group35.microservice.post.model.post.Tag_;
import ru.skillbox.diplom.group35.microservice.post.repository.post.PostRepository;
import ru.skillbox.diplom.group35.microservice.post.resource.post.PostControllerImpl;

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
  private final PostMapper postMapper;

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
      if (tags == null) {
        return builder.conjunction();
      }
      Join<Post, Tag> join = root.join(Post_.tags);
      return builder.in(join.get(Tag_.NAME)).value(tags);
    };
  }

  public Page<PostDto> getAll(PostSearchDto searchDto, Pageable pageable) {
    log.info("getAll(): searchDto:{}, pageable:{}", searchDto, pageable);
    Page<Post> result = postRepository.findAll(getSpecByAllFields(searchDto), pageable);
    return result.map(postMapper::toPostDto);
  }

  public PostDto getById(UUID id) {
    log.info("getById(): postId :{}", id);
    return postMapper.toPostDto(postRepository.getById(id));
  }

  public PostDto create(PostDto postDto) {
    log.info("create(): postDto:{}", postDto);

    if (postDto.getAuthorId() == null) {
      postDto.setAuthorId(PostControllerImpl.getUserId()); //id from token
    }
    postDto.setTime(ZonedDateTime.now());
    postDto.setType(postDto.getPublishDate() != null ? PostType.QUEUED : PostType.POSTED);
    Post post = postMapper.toPost(postDto);
    tagService.saveTags(post);
    return postMapper.toPostDto(postRepository.save(post));
  }

  public PostDto update(PostDto postDto) {
    log.info("update():  postDto:{}", postDto);
    Post post = postRepository.getById(postDto.getId());
    post = postMapper.updatePostFromDto(postDto, post);
    tagService.saveTags(post);
    return postMapper.toPostDto(post);
  }

  public void deleteById(UUID id) {
    log.info("deleteById(): postId :{}", id);
    postRepository.deleteById(id);
  }
}
