package ru.skillbox.diplom.group35.microservice.post.dto.post;

import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseSearchDto;

/**
 * TagSearchDto
 *
 * @author Marat Safagareev
 */
@Getter
@Setter
public class TagSearchDto extends BaseSearchDto {

  private String name;
}
