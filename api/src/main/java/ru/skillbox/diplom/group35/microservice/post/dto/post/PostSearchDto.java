package ru.skillbox.diplom.group35.microservice.post.dto.post;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseSearchDto;

/**
 * PostSearchDto
 *
 * @author Marat Safagareev
 */

@Getter
@Setter
public class PostSearchDto extends BaseSearchDto {

  private List<UUID> ids;
  private List<UUID> accountIds;
  private List<UUID> blockedIds;
  private String author;
  private Boolean withFriends;
  private Set<String> tags = new TreeSet<>();
  private ZonedDateTime dateFrom;
  private ZonedDateTime dateTo;
}
