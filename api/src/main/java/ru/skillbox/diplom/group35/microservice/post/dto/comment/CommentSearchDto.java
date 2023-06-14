package ru.skillbox.diplom.group35.microservice.post.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseSearchDto;
import ru.skillbox.diplom.group35.microservice.post.model.comment.CommentType;

import java.util.UUID;

@Data
@Schema(description = "DTO для поиска комментов")
public class CommentSearchDto extends BaseSearchDto {

    @Schema(description = "Тип комментария: POST, COMMENT")
    private CommentType commentType;

    @Schema(description = "ID автора комментария")
    private UUID authorId;

    @Schema(description = "ID родителя комментария")
    private UUID parentId;

    @Schema(description = "ID поста, к которому относится комментарий")
    private UUID postId;
}
