package ru.skillbox.diplom.group35.microservice.post.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseSearchDto;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CommentSearchDto extends BaseSearchDto {

    private CommentType commentType;

    private UUID authorId;

    private UUID parentId;

    private UUID postId;
    public CommentSearchDto(UUID postId, CommentType commentType) {
        this.commentType = commentType;
        this.postId = postId;
    }

    public CommentSearchDto(UUID postId, UUID parentId, CommentType commentType) {
        this.commentType = commentType;
        this.parentId = parentId;
        this.postId = postId;
    }
}
