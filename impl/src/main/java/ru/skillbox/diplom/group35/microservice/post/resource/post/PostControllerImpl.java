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

  final CommentService commentService;
  private final PostService postService;

  @Override
  public ResponseEntity<Page<PostDto>> getAll(PostSearchDto postSearchDto, Pageable pageable) {
    return null;
  }

  @Override
  public ResponseEntity<PostDto> getById(UUID id) {
    log.info("getById(): PostId :{}", id);
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
    log.info("deleteById(): PostId :{}", id);
    postService.deleteById(id);
  }

  @Override
  public ResponseEntity<CommentDto> createSubComment(CommentDto commentDto, UUID id,
      UUID commentId) {
    return ResponseEntity.ok(commentService.createSubComment(commentDto, id, commentId));
  }

  @Override
  public ResponseEntity<CommentDto> deleteComment(UUID id, UUID commentId) {
    commentService.deleteComment(id, commentId);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Page<CommentDto>> getComment(UUID id, Pageable page) {
    return ResponseEntity.ok(commentService.getComments(id, page));
  }

  @Override
  public ResponseEntity<CommentDto> createComment(UUID id, CommentDto commentDto) {
    return ResponseEntity.ok(commentService.createComment(commentDto, id));
  }

  @Override
  public ResponseEntity<Page<CommentDto>> getSubComment(UUID id, UUID commentId, Pageable page) {
    return ResponseEntity.ok(commentService.getSubComments(id, commentId, page));
  }

}
