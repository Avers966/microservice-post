package ru.skillbox.diplom.group35.microservice.post.resource.post;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group35.library.core.annotation.EnableExceptionHandler;
import ru.skillbox.diplom.group35.microservice.post.dto.tag.TagDto;
import ru.skillbox.diplom.group35.microservice.post.dto.tag.TagSearchDto;
import ru.skillbox.diplom.group35.microservice.post.service.tag.TagService;

/**
 * TagControllerImpl
 *
 * @author Marat Safagareev
 */
@Slf4j
@RestController
@EnableExceptionHandler
@RequiredArgsConstructor
public class TagControllerImpl implements TagController {

  private final TagService tagService;

  @Override
  public ResponseEntity<List<TagDto>> getAdviceTags(TagSearchDto tagSearchDto) {
    log.info("getAdviceTags(): tagSearchDto:{}", tagSearchDto);
    return ResponseEntity.ok(tagService.getAdviceTags(tagSearchDto));
  }
}
