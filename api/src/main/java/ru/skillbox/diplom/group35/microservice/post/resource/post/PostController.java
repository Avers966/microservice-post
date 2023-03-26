package ru.skillbox.diplom.group35.microservice.post.resource.post;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group35.library.core.controller.BaseController;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostSearchDto;
@RequestMapping(value = "/api/v1/post")
public interface PostController extends BaseController<PostDto, PostSearchDto> {

  @Override
  @GetMapping
  ResponseEntity<Page<PostDto>> getAll(PostSearchDto postSearchDto, Pageable pageable);

  @Override
  @GetMapping("/{uuid}")
  ResponseEntity<PostDto> getById(@PathVariable(name = "uuid") UUID id);

  @Override
  @PostMapping
  ResponseEntity<PostDto> create(@RequestBody PostDto postDto);

  @Override
  @PutMapping
  ResponseEntity<PostDto> update(@RequestBody PostDto postDto);

  @Override
  @DeleteMapping(value = "/{uuid}")
  void deleteById(@PathVariable(name = "uuid") UUID id);

  @PutMapping(value = "/{uuid}/comment/{commentUUID}")
  ResponseEntity<CommentDto> createSubComment(@RequestBody CommentDto commentDto,
                                              @PathVariable UUID uuid,
                                              @PathVariable UUID commentUUID);

  @DeleteMapping(value = "/{uuid}/comment/{commentUUID}")
  ResponseEntity<CommentDto> deleteComment(@PathVariable UUID uuid, @PathVariable UUID commentUUID);

  @GetMapping(value = "/{uuid}/comment")
  ResponseEntity<Page<CommentDto>> getComment(@PathVariable UUID uuid, Pageable page);

  @PostMapping(value = "/{uuid}/comment")
  ResponseEntity<CommentDto> createComment(@PathVariable UUID uuid, @RequestBody CommentDto commentDto);

  @GetMapping(value = "/{uuid}/comment/{commentUUID}/subcomment")
  ResponseEntity<Page<CommentDto>> getSubComment(@PathVariable UUID uuid, @PathVariable UUID commentUUID, Pageable page);
}
