package ru.skillbox.diplom.group35.microservice.post.resource.post;


import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skillbox.diplom.group35.microservice.post.dto.tag.TagDto;
import ru.skillbox.diplom.group35.microservice.post.dto.tag.TagSearchDto;

/**
 * TagController
 *
 * @author Marat Safagareev
 */
@Tag(name = "Tag Service", description = "Работа с тегами")
@ApiResponse(responseCode = "200",
        description = "Successful operation",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = ""))
@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = ""))
@RequestMapping(value = "/api/v1/tag")
public interface TagController {

  @GetMapping
  @SecurityRequirement(name = "JWT")
  @Operation(description = "Получение тегов")
  ResponseEntity<List<TagDto>> getAdviceTags(TagSearchDto tagSearchDto);
}
