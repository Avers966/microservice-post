package ru.skillbox.diplom.group35.microservice.post.resource.post;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skillbox.diplom.group35.library.core.controller.BaseController;
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

}
