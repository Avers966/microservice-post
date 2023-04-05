package ru.skillbox.diplom.group35.microservice.post.mapper.post;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;

@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface PostMapper {

  PostDto toPostDto(Post post);

  @Mapping(source = "isDeleted", target = "isDeleted", defaultValue = "false")
  @Mapping(source = "commentsCount", target = "commentsCount", defaultValue = "0")
  @Mapping(source = "likeAmount", target = "likeAmount", defaultValue = "0")
  @Mapping(source = "isBlocked", target = "isBlocked", defaultValue = "false")
  @Mapping(source = "myLike", target = "myLike", defaultValue = "false")
  Post toPost(PostDto postDto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Post updatePostFromDto(PostDto postDto, @MappingTarget Post post);
}
