package ru.skillbox.diplom.group35.microservice.post.resource.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.dto.StatisticResponseDto;

/**
 * StatisticController
 *
 * @author Vladimir Polochanin
 */

@RequestMapping("/api/v1/post/statistic")
public interface StatisticController {
    @GetMapping("/post")
    ResponseEntity<StatisticResponseDto> getPostStatistic(PostStatisticRequestDto requestDto);

    @GetMapping("/comment")
    ResponseEntity<StatisticResponseDto> getCommentStatistic(PostStatisticRequestDto requestDto);
    @GetMapping("/like")
    ResponseEntity<StatisticResponseDto> getLikeStatistic(PostStatisticRequestDto requestDto);
}
