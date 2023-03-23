package ru.skillbox.diplom.group35.microservice.post.model.post;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.model.base.BaseEntity;


/**
 * Post
 *
 * @author Marat Safagareev
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {

  @Column(name = "created_time", columnDefinition = "timestamp with time zone", nullable = false)
  private ZonedDateTime createdTime;

  @Column(name = "changed_time", columnDefinition = "timestamp with time zone")
  private ZonedDateTime changedTime;

  @Column(name = "author_id", columnDefinition = "uuid", nullable = false)
  private UUID authorId;

  @Column(name = "title", columnDefinition = "varchar(255)", nullable = false)
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", columnDefinition = "varchar(255)", nullable = false)
  private PostType type;

  @Column(name = "post_text", columnDefinition = "text", nullable = false)
  private String postText;

  @Column(name = "is_blocked", columnDefinition = "boolean", nullable = false)
  private boolean isBlocked;

  @Column(name = "comments_count", columnDefinition = "integer")
  private Integer commentsCount;

  @Transient
  @ElementCollection
  private Set<String> tags = new TreeSet<>();  // tags coming soon!

  @Column(name = "like_amount", columnDefinition = "integer")
  private Integer likeAmount;

  @Column(name = "my_like", columnDefinition = "boolean", nullable = false)
  private boolean myLike;

  @Column(name = "image_path", columnDefinition = "varchar(255)")
  private String imagePath;

  @Column(name = "publish_date", columnDefinition = "timestamp with time zone", nullable = false)
  private ZonedDateTime publishDate;
}
