package ru.skillbox.diplom.group35.microservice.post.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;
import ru.skillbox.diplom.group35.microservice.post.model.comment.CommentType;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Schema(description = "DTO коммента")
public class CommentDto extends BaseDto {

    @Schema(description = "Тип комментария: POST - комментарий к посту, COMMENT - комментарий к комментарию, субкомментарий")
    private CommentType commentType;

    @Schema(description = "Время создания комментария")
    private ZonedDateTime time;

    @Schema(description = "Время изменения комментария")
    private ZonedDateTime timeChanged;

    @Schema(description = "ID автора комментария")
    private UUID authorId;

    @Schema(description = "ID родителя, к которому был оставлен коммнетарий")
    private UUID parentId;

    @Schema(description = "Текст комментария")
    private String commentText;

    @Schema(description = "ID поста, к которому относится комментарий")
    private UUID postId;

    @Schema(description = "Заблокирован ли?")
    private Boolean isBlocked;

    @Schema(description = "Количество лайков комментария")
    private Long likeAmount;

    @Schema(description = "Это той лайк?")
    private Boolean myLike;

    @Schema(description = "Количество комментариев")
    private Long commentsCount;

    @Schema(description = "Путь к изображению")
    private String imagePath;
}
