package ru.skillbox.diplom.group35.microservice.post.dto.post;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;
import ru.skillbox.diplom.group35.microservice.post.dto.like.ReactionDto;
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

  private ZonedDateTime time;
  private ZonedDateTime timeChanged;
  private UUID authorId;
  private String title;
  private PostType type;
  private String postText;
  private Boolean isBlocked;
  private Integer commentsCount;
  private Set<TagDto> tags;
  private Set<ReactionDto> reactions = new HashSet<>();
  private String myReaction;
  private Integer likeAmount;
  private Boolean myLike;
  private String imagePath;
  private ZonedDateTime publishDate;
}
