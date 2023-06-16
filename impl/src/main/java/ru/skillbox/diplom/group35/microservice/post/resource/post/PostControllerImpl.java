package ru.skillbox.diplom.group35.microservice.post.resource.post;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group35.library.core.annotation.EnableExceptionHandler;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentDto;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentSearchDto;
import ru.skillbox.diplom.group35.microservice.post.dto.like.LikeDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostSearchDto;
import ru.skillbox.diplom.group35.microservice.post.model.like.LikeType;
import ru.skillbox.diplom.group35.microservice.post.service.comment.CommentService;
import ru.skillbox.diplom.group35.microservice.post.service.like.LikeService;
import ru.skillbox.diplom.group35.microservice.post.service.post.DelayedPostService;
import ru.skillbox.diplom.group35.microservice.post.service.post.PostService;

/**
 * PostResourceImpl
 *
 * @author Marat Safagareev
 */

@Slf4j
@RestController
@EnableExceptionHandler
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {

  private final CommentService commentService;
  private final LikeService likeService;
  private final PostService postService;
  private final DelayedPostService delayedPostService;

  @Override
  public ResponseEntity<Page<PostDto>> getAll(PostSearchDto postSearchDto, Pageable pageable) {
    return ResponseEntity.ok(postService.getAll(postSearchDto, pageable));
  }

  @Override
  public ResponseEntity<PostDto> getById(UUID id) {
    return ResponseEntity.ok(postService.getById(id));
  }

  @Override
  public ResponseEntity<PostDto> create(PostDto postDto) {
    return ResponseEntity.ok(postService.create(postDto));
  }

  @Override
  public ResponseEntity<?> getDelayedPost() {
    delayedPostService.publishPost();
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<PostDto> update(PostDto postDto) {
    return ResponseEntity.ok(postService.update(postDto));
  }

  @Override
  public void deleteById(UUID id) {
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
  public ResponseEntity<Page<CommentDto>> getComment(UUID postId, CommentSearchDto searchDto, Pageable page) {
    return ResponseEntity.ok(commentService.getComments(postId, searchDto, page));
  }

  @Override
  public ResponseEntity<CommentDto> createComment(UUID id, CommentDto commentDto) {
    return ResponseEntity.ok(commentService.createComment(commentDto, id));
  }

  @Override
  public ResponseEntity<Page<CommentDto>> getSubComment(UUID postId, UUID commentId, CommentSearchDto searchDto, Pageable page) {
    return ResponseEntity.ok(commentService.getSubComments(postId, commentId, searchDto, page));
  }
  @Override
  public ResponseEntity<CommentDto> updateComment(UUID id, CommentDto commentDto) {
    return ResponseEntity.ok(commentService.updateComment(commentDto, id));
  }
  @Override
  public ResponseEntity<LikeDto> createPostLike(UUID id, LikeDto likeDto) {
    return ResponseEntity.ok(likeService.createLike(id, likeDto, LikeType.POST));
  }

  @Override
  public ResponseEntity<LikeDto> deletePostLike(UUID id) {
    likeService.deleteLike(id, LikeType.POST);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<LikeDto> createCommentLike(UUID id, UUID commentId) {
    return ResponseEntity.ok(likeService.createLike(commentId, new LikeDto(), LikeType.COMMENT));
  }
  @Override
  public ResponseEntity<LikeDto> deleteCommentLike(UUID id, UUID commentId) {
    likeService.deleteLike(commentId, LikeType.COMMENT);
    return ResponseEntity.ok().build();
  }
}
