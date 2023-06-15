package ru.skillbox.diplom.group35.microservice.post.model.post;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import ru.skillbox.diplom.group35.library.core.model.base.BaseEntity;
import ru.skillbox.diplom.group35.microservice.post.model.tag.Tag;


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

  @Column(name = "time", columnDefinition = "timestamp with time zone", nullable = false)
  private ZonedDateTime time;

  @Column(name = "time_changed", columnDefinition = "timestamp with time zone")
  private ZonedDateTime timeChanged;

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
  private Boolean isBlocked;

  @Column(name = "comments_count", columnDefinition = "integer")
  private Integer commentsCount;

  @ManyToMany
  @JoinTable(
      name = "post_tag",
      joinColumns = {@JoinColumn(name = "post_id")},
      inverseJoinColumns = {@JoinColumn(name = "tag_id")}
  )
  @Cascade(CascadeType.SAVE_UPDATE)
  private Set<Tag> tags;

  @Column(name = "like_amount", columnDefinition = "integer")
  private Integer likeAmount;

  @Column(name = "my_like", columnDefinition = "boolean")
  private Boolean myLike;

  @Column(name = "image_path", columnDefinition = "varchar(255)")
  private String imagePath;

  @Column(name = "publish_date", columnDefinition = "timestamp with time zone")
  private ZonedDateTime publishDate;
}
