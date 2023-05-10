package ru.skillbox.diplom.group35.microservice.post.dto.comment;

import lombok.*;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;
import ru.skillbox.diplom.group35.microservice.post.model.comment.CommentType;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class CommentDto extends BaseDto {

    private CommentType commentType;

    private ZonedDateTime time;

    private ZonedDateTime timeChanged;

    private UUID authorId;

    private UUID parentId;

    private String commentText;

    private UUID postId;

    private Boolean isBlocked;

    private Long likeAmount;

    private Boolean myLike;

    private Long commentsCount;

    private String imagePath;
}
