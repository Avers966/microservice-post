package ru.skillbox.diplom.group35.microservice.post.model.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.model.base.BaseEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "comment")
@RequiredArgsConstructor
public class Comment extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_type", columnDefinition = "varchar(255)")
    private CommentType commentType;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime time;

    @Column(name = "time_changed", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime timeChanged;

    @Column(name = "author_id")
    private UUID authorId;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "comment_text", nullable = false)
    private String commentText;

    @Column(name = "post_id")
    private UUID postId;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @Column(name = "like_amount")
    private Integer likeAmount;

    @Column(name = "my_like")
    private Boolean myLike;

    @Column(name = "comments_count")
    private Integer commentsCount;

    @Column(name = "image_path")
    private String imagePath;
}
