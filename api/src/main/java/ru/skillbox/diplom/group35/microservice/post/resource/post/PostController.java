package ru.skillbox.diplom.group35.microservice.post.resource.post;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group35.library.core.controller.BaseController;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentDto;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentSearchDto;
import ru.skillbox.diplom.group35.microservice.post.dto.like.LikeDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostSearchDto;
@Tag(name = "Post Service", description = "Работа с постами, комментариями, лайками")
@ApiResponse(responseCode = "200",
              description = "Successful operation",
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = ""))
@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = ""))
@RequestMapping(value = "/api/v1/post")
public interface PostController extends BaseController<PostDto, PostSearchDto> {

  @Override
  @GetMapping
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Получение постов")
  ResponseEntity<Page<PostDto>> getAll(PostSearchDto postSearchDto, Pageable pageable);

  @Override
  @GetMapping("/{id}")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Получение поста")
  ResponseEntity<PostDto> getById(@PathVariable(name = "id") UUID id);

  @Override
  @PostMapping
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Создание поста")
  ResponseEntity<PostDto> create(@RequestBody PostDto postDto);

  @PutMapping("/delayed")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Отложенный пост")
  ResponseEntity<?> getDelayedPost();

  @Override
  @PutMapping
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Редактирование поста")
  ResponseEntity<PostDto> update(@RequestBody PostDto postDto);

  @Override
  @DeleteMapping(value = "/{id}")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Удаление поста")
  void deleteById(@PathVariable(name = "id") UUID id);

  @PutMapping(value = "/{id}/comment/{commentId}")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Создание сабкомментария")
  ResponseEntity<CommentDto> createSubComment(@RequestBody CommentDto commentDto,
                                              @PathVariable UUID id,
                                              @PathVariable UUID commentId);

  @DeleteMapping(value = "/{id}/comment/{commentId}")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Удаление комментария")
  ResponseEntity<CommentDto> deleteComment(@PathVariable UUID id, @PathVariable UUID commentId);

  @GetMapping(value = "/{postId}/comment")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Получение комментариев")
  ResponseEntity<Page<CommentDto>> getComment(@PathVariable UUID postId, CommentSearchDto searchDto, Pageable page);

  @PostMapping(value = "/{id}/comment")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Создание комментария к посту")
  ResponseEntity<CommentDto> createComment(@PathVariable UUID id, @RequestBody CommentDto commentDto);

  @PutMapping(value = "/{id}/comment")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Редактирование комментария")
  ResponseEntity<CommentDto> updateComment(@PathVariable UUID id,
                                           @RequestBody CommentDto commentDto);

  @GetMapping(value = "/{postId}/comment/{commentId}/subcomment")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Получение сабкомментариев")
  ResponseEntity<Page<CommentDto>> getSubComment(@PathVariable UUID postId,
                                                 @PathVariable UUID commentId,
                                                 CommentSearchDto searchDto,
                                                 Pageable page);
  @PostMapping(value = "/{id}/like")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Создание лайка типа POST")
  ResponseEntity<LikeDto> createPostLike(@PathVariable UUID id, @RequestBody LikeDto likeDto);

  @DeleteMapping(value = "/{id}/like")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Удаление лайка типа POST")
  ResponseEntity<LikeDto> deletePostLike(@PathVariable UUID id);

  @PostMapping(value = "/{id}/comment/{commentId}/like")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Создание лайка типа COMMENT")
  ResponseEntity<LikeDto> createCommentLike(@PathVariable UUID id, @PathVariable UUID commentId);

  @DeleteMapping(value = "/{id}/comment/{commentId}/like")
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Создание лайка типа COMMENT")
  ResponseEntity<LikeDto> deleteCommentLike(@PathVariable UUID id, @PathVariable UUID commentId);
}
