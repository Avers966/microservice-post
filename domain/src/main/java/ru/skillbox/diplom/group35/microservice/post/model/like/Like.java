package ru.skillbox.diplom.group35.microservice.post.model.like;

import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@Table(name = "like")
@RequiredArgsConstructor
public class Like extends BaseEntity {

  @Column(name = "author_id", columnDefinition = "uuid", nullable = false)
  private UUID authorId;

  @Column(name = "time", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
  private ZonedDateTime time;

  @Column(name = "item_id", columnDefinition = "uuid", nullable = false)
  private UUID itemId;

  @Enumerated(EnumType.STRING)
  private LikeType type;

  @Column(name = "reaction_type", columnDefinition = "varchar(255)")
  private String reactionType;
}
