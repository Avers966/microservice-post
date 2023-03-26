package ru.skillbox.diplom.group35.microservice.post.resource.post;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostSearchDto;
import ru.skillbox.diplom.group35.microservice.post.service.comment.CommentService;
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
  final CommentService commentService;

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

  @Override
  public ResponseEntity<CommentDto> createSubComment(CommentDto commentDto, UUID uuid, UUID commentUUID) {
    return ResponseEntity.ok(commentService.createSubComment(commentDto, uuid, commentUUID));
  }

  @Override
  public ResponseEntity<CommentDto> deleteComment(UUID uuid, UUID commentUUID) {
    commentService.deleteComment(uuid, commentUUID);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Page<CommentDto>> getComment(UUID uuid, Pageable page) {
    return ResponseEntity.ok(commentService.getComments(uuid, page));
  }

  @Override
  public ResponseEntity<CommentDto> createComment(UUID uuid, CommentDto commentDto) {
    return ResponseEntity.ok(commentService.createComment(commentDto, uuid));
  }

  @Override
  public ResponseEntity<Page<CommentDto>> getSubComment(UUID uuid, UUID commentUUID, Pageable page) {
    return ResponseEntity.ok(commentService.getSubComments(uuid, commentUUID, page));
  }

}
