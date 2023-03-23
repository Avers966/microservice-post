package ru.skillbox.diplom.group35.microservice.post.resource.post;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostSearchDto;
import ru.skillbox.diplom.group35.microservice.post.service.post.PostService;

/**
 * PostResourceImpl
 *
 * @author Marat Safagareev
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {

  private final PostService postService;

  @Override
  public ResponseEntity<Page<PostDto>> getAll(PostSearchDto postSearchDto, Pageable pageable) {
    return null;
  }

  @Override
  public ResponseEntity<PostDto> getById(UUID id) {
    log.info("getById(): PostUUID :{}", id);
    return (postService.getById(id) != null) ? ResponseEntity.ok(postService.getById(id))
        : ResponseEntity.badRequest().build();
  }

  @Override
  public ResponseEntity<PostDto> create(PostDto postDto) {
    log.info("create(): PostDto:{}", postDto);
    return ResponseEntity.ok(postService.create(postDto));
  }

  @Override
  public ResponseEntity<PostDto> update(PostDto postDto) {
    log.info("update():  PostDto:{}", postDto);
    return (postService.update(postDto) != null) ? ResponseEntity.ok(postService.update(postDto))
        : ResponseEntity.badRequest().build();
  }

  @Override
  public void deleteById(UUID id) {
    log.info("deleteById(): PostUUID :{}", id);
    postService.deleteById(id);
  }

}
