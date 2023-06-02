package ru.skillbox.diplom.group35.microservice.post.dto.post;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseSearchDto;

/**
 * PostSearchDto
 *
 * @author Marat Safagareev
 */

@Getter
@Setter
@Schema(description = "DTO для поиска постов")
public class PostSearchDto extends BaseSearchDto {

  @Schema(description = "ID постов")
  private List<UUID> ids;

  @Schema(description = "ID аккаунтов авторов постов")
  private List<UUID> accountIds;

  @Schema(description = "ID заблокированных аккаунтов авторов постов")
  private List<UUID> blockedIds;

  @Schema(description = "Автор")
  private String author;

  @Schema(description = "С друзьями?")
  private Boolean withFriends;

  @Schema(description = "Теги поста")
  private Set<String> tags;

  @Schema(description = "Дата от")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private ZonedDateTime dateFrom;

  @Schema(description = "Дата до")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private ZonedDateTime dateTo;
}
