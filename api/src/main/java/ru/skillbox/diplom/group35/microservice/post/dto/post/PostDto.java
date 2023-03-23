package ru.skillbox.diplom.group35.microservice.post.dto.post;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;
import ru.skillbox.diplom.group35.microservice.post.model.post.PostType;


/**
 * PostDto
 *
 * @author Marat Safagareev
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto extends BaseDto {

  private ZonedDateTime createdTime;
  private ZonedDateTime changedTime;
  private UUID authorId;
  private String title;
  private PostType type;
  private String postText;
  private boolean isBlocked;
  private Integer commentsCount;
  private Set<String> tags = new TreeSet<>();
  private Integer likeAmount;
  private boolean myLike;
  private String imagePath;
  private ZonedDateTime publishDate;
}
