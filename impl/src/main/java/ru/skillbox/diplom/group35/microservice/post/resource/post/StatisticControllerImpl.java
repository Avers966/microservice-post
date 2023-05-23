package ru.skillbox.diplom.group35.microservice.post.resource.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group35.library.core.annotation.EnableExceptionHandler;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.dto.like.LikeStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.dto.StatisticResponseDto;
import ru.skillbox.diplom.group35.microservice.post.service.StatisticService;

/**
 * StatisticControllerImpl
 *
 * @author Vladimir Polochanin
 */
@Slf4j
@RestController
@EnableExceptionHandler
@RequiredArgsConstructor
public class StatisticControllerImpl implements StatisticController {

    private final StatisticService statisticService;

    @Override
    public ResponseEntity<StatisticResponseDto> getPostStatistic(PostStatisticRequestDto requestDto) {
        log.info("Post get statistic");
        return ResponseEntity.ok(statisticService.getPostStatistic(requestDto));

    }

    @Override
    public ResponseEntity<StatisticResponseDto> getCommentStatistic(CommentStatisticRequestDto requestDto) {
        log.info("Post get statistic from Comment");
        return ResponseEntity.ok(statisticService.getCommentStatistic(requestDto));
    }

    @Override
    public ResponseEntity<StatisticResponseDto> getLikeStatistic(LikeStatisticRequestDto requestDto) {
        log.info("Post get statistic from Like");
        return ResponseEntity.ok(statisticService.getLikeStatistic(requestDto));
    }
}
