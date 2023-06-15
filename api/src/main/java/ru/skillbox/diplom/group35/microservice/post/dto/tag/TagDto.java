package ru.skillbox.diplom.group35.microservice.post.dto.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseDto;

/**
 * TagDto
 *
 * @author Marat Safagareev
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO тега")
public class TagDto extends BaseDto {

  @Schema(description = "Имя тега")
  private String name;
}
