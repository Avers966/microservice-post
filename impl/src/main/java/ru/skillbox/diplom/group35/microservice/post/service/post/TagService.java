package ru.skillbox.diplom.group35.microservice.post.service.post;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.post.dto.post.TagDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.TagSearchDto;
import ru.skillbox.diplom.group35.microservice.post.mapper.post.TagMapper;
import ru.skillbox.diplom.group35.microservice.post.repository.post.TagRepository;

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
    log.info("getAdviceTags(): tagSearchDto:{}", tagSearchDto);
    Pageable topFive = PageRequest.of(0, 5);
    return tagMapper.toTagDtoList(tagRepository.findAdviceTags(tagSearchDto.getName(), topFive));
  }

}
