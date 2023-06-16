package ru.skillbox.diplom.group35.microservice.post.resource.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skillbox.diplom.group35.microservice.post.dto.statistic.PostStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.dto.statistic.StatisticResponseDto;

/**
 * StatisticController
 *
 * @author Vladimir Polochanin
 */
@Tag(name = "Statistic Service", description = "Статистика постов, лайков, комментариев")
@ApiResponse(responseCode = "200",
                description = "Successful operation",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = ""))
@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = ""))
@RequestMapping("/api/v1/post")
public interface StatisticController {
    @GetMapping("/statistic/post")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Получение статистики постов")
    ResponseEntity<StatisticResponseDto> getPostStatistic(@SpringQueryMap PostStatisticRequestDto requestDto);

    @GetMapping("/statistic/comment")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Получение статистики комментариев")
    ResponseEntity<StatisticResponseDto> getCommentStatistic(@SpringQueryMap PostStatisticRequestDto requestDto);

    @GetMapping("/statistic/like")
    @SecurityRequirement(name = "JWT")
    @Operation(description = "Получение статистики лайков")
    ResponseEntity<StatisticResponseDto> getLikeStatistic(@SpringQueryMap PostStatisticRequestDto requestDto);
}
