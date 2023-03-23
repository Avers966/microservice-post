package ru.skillbox.diplom.group35.microservice.post.service.post;

import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.mapper.post.PostMapper;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;
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
  private final PostMapper postMapper;

  public PostDto getById(UUID id) {
    log.info("getById(): PostUUID :{}", id);
    Optional<Post> finder = postRepository.findById(id);
    log.info("getById():  findById:{}", finder);
    return finder.map(postMapper::toPostDto).orElse(null);
  }

  public PostDto create(PostDto postDto) {
    log.info("create(): PostDto:{}", postDto);
    return postMapper.toPostDto(postRepository.save(postMapper.toPost(postDto)));

  }

  public PostDto update(PostDto postDto) {
    log.info("update():  PostDto:{}", postDto);
    Optional<Post> finder = postRepository.findById(postDto.getId());
    log.info("update():  findById:{}", finder);
    return finder.map(post -> postMapper.toPostDto(postMapper.updatePostFromDto(postDto, post)))
        .orElse(null);
  }

  public void deleteById(UUID id) {
    log.info("deleteById(): PostUUID :{}", id);
    postRepository.deleteById(id);
  }
}
