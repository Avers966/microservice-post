package ru.skillbox.diplom.group35.microservice.post.mapper.post;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

  PostDto toPostDto(Post post);

  List<PostDto> toPostDtos(List<Post> posts);

  Post toPost(PostDto postDto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Post updatePostFromDto(PostDto postDto, @MappingTarget Post post);
}
