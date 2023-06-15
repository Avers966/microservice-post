package ru.skillbox.diplom.group35.microservice.post.mapper.tag;

import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group35.microservice.post.dto.tag.TagDto;
import ru.skillbox.diplom.group35.microservice.post.model.tag.Tag;

/**
 * TagMapper
 *
 * @author Marat Safagareev
 */
@Mapper(componentModel = "spring")
public interface TagMapper {

  TagDto toTagDto(Tag tag);

  List<TagDto> toTagDtoList(List<Tag> tags);

  Set<TagDto> toTagDtos(Set<Tag> tags);

  Set<Tag> toTags(Set<TagDto> tagDtos);

  @Mapping(source = "isDeleted", target = "isDeleted", defaultValue = "false")
  Tag toTag(TagDto tagDto);
}
