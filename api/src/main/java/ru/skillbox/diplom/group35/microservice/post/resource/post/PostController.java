package ru.skillbox.diplom.group35.microservice.post.resource.post;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group35.library.core.controller.BaseController;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentDto;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentSearchDto;
import ru.skillbox.diplom.group35.microservice.post.dto.like.LikeDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostSearchDto;
@RequestMapping(value = "/api/v1/post")
public interface PostController extends BaseController<PostDto, PostSearchDto> {

  @Override
  @GetMapping
  ResponseEntity<Page<PostDto>> getAll(PostSearchDto postSearchDto, Pageable pageable);

  @Override
  @GetMapping("/{id}")
  ResponseEntity<PostDto> getById(@PathVariable(name = "id") UUID id);

  @Override
  @PostMapping
  ResponseEntity<PostDto> create(@RequestBody PostDto postDto);

  @Override
  @PutMapping
  ResponseEntity<PostDto> update(@RequestBody PostDto postDto);

  @Override
  @DeleteMapping(value = "/{id}")
  void deleteById(@PathVariable(name = "id") UUID id);

  @PutMapping(value = "/{id}/comment/{commentId}")
  ResponseEntity<CommentDto> createSubComment(@RequestBody CommentDto commentDto,
                                              @PathVariable UUID id,
                                              @PathVariable UUID commentId);

  @DeleteMapping(value = "/{id}/comment/{commentId}")
  ResponseEntity<CommentDto> deleteComment(@PathVariable UUID id, @PathVariable UUID commentId);

  @GetMapping(value = "/{postId}/comment")
  ResponseEntity<Page<CommentDto>> getComment(@PathVariable UUID postId, CommentSearchDto searchDto, Pageable page);

  @PostMapping(value = "/{id}/comment")
  ResponseEntity<CommentDto> createComment(@PathVariable UUID id, @RequestBody CommentDto commentDto);
  @PutMapping(value = "/{id}/comment")
  ResponseEntity<CommentDto> updateComment(@PathVariable UUID id,
                                           @RequestBody CommentDto commentDto);

  @GetMapping(value = "/{postId}/comment/{commentId}/subcomment")
  ResponseEntity<Page<CommentDto>> getSubComment(@PathVariable UUID postId,
                                                 @PathVariable UUID commentId,
                                                                CommentSearchDto searchDto,
                                                                Pageable page);

  @PostMapping(value = "/{id}/like")
  ResponseEntity<LikeDto> createPostLike(@PathVariable UUID id);

  @DeleteMapping(value = "/{id}/like")
  ResponseEntity<LikeDto> deletePostLike(@PathVariable UUID id);

  @PostMapping(value = "/{id}/comment/{commentId}/like")
  ResponseEntity<LikeDto> createCommentLike(@PathVariable UUID id, @PathVariable UUID commentId);

  @DeleteMapping(value = "/{id}/comment/{commentId}/like")
  ResponseEntity<LikeDto> deleteCommentLike(@PathVariable UUID id, @PathVariable UUID commentId);
}
