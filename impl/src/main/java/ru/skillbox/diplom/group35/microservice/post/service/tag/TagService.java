package ru.skillbox.diplom.group35.microservice.post.service.tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group35.microservice.post.dto.tag.TagDto;
import ru.skillbox.diplom.group35.microservice.post.dto.tag.TagSearchDto;
import ru.skillbox.diplom.group35.microservice.post.mapper.tag.TagMapper;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;
import ru.skillbox.diplom.group35.microservice.post.model.tag.Tag;
import ru.skillbox.diplom.group35.microservice.post.repository.tag.TagRepository;

/**
 * TagService
 *
 * @author Marat Safagareev
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

  private final TagRepository tagRepository;
  private final TagMapper tagMapper;

  public List<TagDto> getAdviceTags(TagSearchDto tagSearchDto) {
    Pageable topFive = PageRequest.of(0, 5);
    return tagMapper.toTagDtoList(tagRepository.findAdviceTags(tagSearchDto.getName(), topFive));
  }

  public Set<Tag> saveTags(Post post) {
    Set<Tag> tagSet = post.getTags();
    tagSet.forEach(t -> t.setIsDeleted(false));
    post.getTags().stream().filter(t -> t.getId() == null).forEach(tagRepository::save);
    return tagSet;
  }

  public Set<TagDto> getTags(Set<Tag> tagSet) {
    Set<TagDto> tags = new HashSet<>();
    for (Tag tag : tagSet) {
      tags.add(tagMapper.toTagDto(tag));
    }
    return tags;
  }

}
