package ru.skillbox.diplom.group35.microservice.post.resource.post;


import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skillbox.diplom.group35.microservice.post.dto.post.TagDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.TagSearchDto;

/**
 * TagController
 *
 * @author Marat Safagareev
 */
@RequestMapping(value = "/api/v1/tag")
public interface TagController {

  @GetMapping
  ResponseEntity<List<TagDto>> getAdviceTags(TagSearchDto tagSearchDto);
}
