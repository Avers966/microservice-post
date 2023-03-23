package ru.skillbox.diplom.group35.microservice.post.model.like;

import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.model.base.BaseEntity;
//import ru.skillbox.diplom.group35.microservice.post.model.comment.CommentType;


/**
 * Like
 *
 * @author Marat Safagareev
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Like extends BaseEntity {
//
//  @Column(name = "author_id", columnDefinition = "uuid", nullable = false)
//  private UUID authorId;
//
//  @Column(name = "created_time", columnDefinition = "timestamp with time zone", nullable = false)
//  private ZonedDateTime createdTime;
//
//  @Column(name = "item_id", columnDefinition = "uuid", nullable = false)
//  private UUID itemId;
//
//  @Column(name = "type", columnDefinition = "varchar(255)", nullable = false)
//  private CommentType type;
}
