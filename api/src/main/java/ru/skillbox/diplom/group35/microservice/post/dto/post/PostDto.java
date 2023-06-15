package ru.skillbox.diplom.group35.microservice.post.dto.post;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;
import ru.skillbox.diplom.group35.microservice.post.dto.like.ReactionDto;
import ru.skillbox.diplom.group35.microservice.post.dto.tag.TagDto;
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
@Schema(description = "DTO поста")
public class PostDto extends BaseDto {

  @Schema(description = "Время создания поста")
  private ZonedDateTime time;

  @Schema(description = "Время изменения поста")
  private ZonedDateTime timeChanged;

  @Schema(description = "ID автора поста")
  private UUID authorId;

  @Schema(description = "Зоголовок поста")
  private String title;

  @Schema(description = "Тип поста")
  private PostType type;

  @Schema(description = "Текст поста: POSTED - опубликован, QUEUED - отложен)")
  private String postText;

  @Schema(description = "Заблокирован ли пост?")
  private Boolean isBlocked;

  @Schema(description = "Количество комментариев к посту")
  private Integer commentsCount;

  @Schema(description = "Теги поста")
  private Set<TagDto> tags;

  @Schema(description = "Список типов реакций")
  private Set<ReactionDto> reactions = new HashSet<>();

  @Schema(description = "Тип реакции пользователя")
  private String myReaction;

  @Schema(description = "Количество лайков")
  private Integer likeAmount;

  @Schema(description = "Есть мой лайк?")
  private Boolean myLike;

  @Schema(description = "Путь к изображению")
  private String imagePath;

  @Schema(description = "Дата и время публикации поста")
  private ZonedDateTime publishDate;
}
