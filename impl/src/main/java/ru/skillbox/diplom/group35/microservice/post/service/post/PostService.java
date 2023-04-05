package ru.skillbox.diplom.group35.microservice.post.service.post;

import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.between;
import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.getBaseSpecification;
import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.in;
import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.notIn;

import java.time.ZonedDateTime;
import java.util.UUID;
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
  private final PostMapper postMapper;

  public static Specification<Post> getSpecByAllFields(PostSearchDto searchDto) {
    return getBaseSpecification(searchDto)
        .and(in(Post_.id, searchDto.getIds(), true))
        .and(in(Post_.authorId, searchDto.getAccountIds(), true))
        .and(notIn(Post_.authorId, searchDto.getBlockedIds(), true))
        .and(between(Post_.time,
            searchDto.getDateFrom() == null ? null : searchDto.getDateFrom(),
            searchDto.getDateFrom() == null ? null : searchDto.getDateTo(), true));
  }


  public Page<PostDto> getAll(PostSearchDto postSearchDto, Pageable pageable) {
    log.info("getAll(): postSearchDto:{}, pageable:{}", postSearchDto, pageable);
    Page<Post> result = postRepository.findAll(getSpecByAllFields(postSearchDto), pageable);
    return result.map(postMapper::toPostDto);
  }

  public PostDto getById(UUID id) {
    log.info("getById(): postId :{}", id);
    return postMapper.toPostDto(postRepository.getById(id));
  }

  public PostDto create(PostDto postDto) {
    log.info("create(): postDto:{}", postDto);

    postDto.setAuthorId(PostControllerImpl.getUserId()); //id from token

    postDto.setTime(ZonedDateTime.now());
    postDto.setType(postDto.getPublishDate() == null ? PostType.POSTED : PostType.QUEUED);
    return postMapper.toPostDto(postRepository.save(postMapper.toPost(postDto)));
  }

  public PostDto update(PostDto postDto) {
    log.info("update():  postDto:{}", postDto);
    Post post = postRepository.getById(postDto.getId());
    return postMapper.toPostDto(postMapper.updatePostFromDto(postDto, post));

  }

  public void deleteById(UUID id) {
    log.info("deleteById(): postId :{}", id);
    postRepository.deleteById(id);
  }

}
