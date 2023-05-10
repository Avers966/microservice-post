package ru.skillbox.diplom.group35.microservice.post.dto.comment;

import lombok.Data;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseSearchDto;
import ru.skillbox.diplom.group35.microservice.post.model.comment.CommentType;

import java.util.UUID;

@Data
public class CommentSearchDto extends BaseSearchDto {

    private CommentType commentType;

    private UUID authorId;

    private UUID parentId;

    private UUID postId;

    public CommentSearchDto(UUID id, CommentType commentType) {
        this.commentType = commentType;
        this.postId = id;

    }

    public CommentSearchDto(UUID id, UUID commentId, CommentType commentType) {
        this.commentType = commentType;
        this.postId = id;
        this.parentId = commentId;
    }
}
