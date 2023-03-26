package ru.skillbox.diplom.group35.microservice.post.mapper.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentDto;
import ru.skillbox.diplom.group35.microservice.post.model.comment.Comment;

@Mapper
public interface CommentMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "isDeleted", source = "isDeleted")
    @Mapping(target = "imagePath", source = "imagePath")
    @Mapping(target = "time", source = "time")
    @Mapping(target = "likeAmount", source = "likeAmount")
    @Mapping(target = "commentsCount", source = "commentsCount")
    @Mapping(target = "myLike", source = "myLike")
    @Mapping(target = "isBlocked", source = "isBlocked")
    @Mapping(target = "authorId", source = "authorId")
    CommentDto convertToDto(Comment comment);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "imagePath", constant = "")
    @Mapping(target = "time", source = "time")
    @Mapping(target = "likeAmount", source = "likeAmount")
    @Mapping(target = "commentsCount", source = "commentsCount")
    @Mapping(target = "commentText", constant = "")
    @Mapping(target = "myLike", source = "myLike")
    @Mapping(target = "isBlocked", source = "isBlocked")
    @Mapping(target = "authorId", source = "authorId")
    Comment convertToEntity(CommentDto commentDto);
}
