package ru.skillbox.diplom.group35.microservice.post.mapper.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentDto;
import ru.skillbox.diplom.group35.microservice.post.model.comment.Comment;

@Mapper
public interface CommentMapper {
    @Mapping(target = "id", source = "id")
    CommentDto convertToDto(Comment comment);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "imagePath", constant = "")
    @Mapping(target = "likeAmount", constant = "0")
    @Mapping(target = "commentsCount", constant = "0")
    @Mapping(target = "myLike", constant = "false")
    @Mapping(target = "isBlocked", constant = "false")
    Comment convertToEntity(CommentDto commentDto);
}
