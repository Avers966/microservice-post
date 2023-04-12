package ru.skillbox.diplom.group35.microservice.post.dto.post;

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
public class TagDto extends BaseDto {

  private String name;
}
