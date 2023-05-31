package ru.skillbox.diplom.group35.microservice.post.dto.like;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.*;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;
import ru.skillbox.diplom.group35.microservice.post.model.like.LikeType;

/**
 * LikeDto
 *
 * @author Marat Safagareev
 */

@Data
public class LikeDto extends BaseDto {

  private UUID authorId;

  private ZonedDateTime time;

  private UUID itemId;

  private LikeType type;

  private String reactionType;

}
