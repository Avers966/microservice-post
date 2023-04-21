package ru.skillbox.diplom.group35.microservice.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.dto.like.LikeStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.dto.StatisticResponseDto;
import ru.skillbox.diplom.group35.microservice.post.service.post.PostStatistic;

import javax.transaction.Transactional;

/**
 * StatisticService
 *
 * @author Vladimir Polochanin
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StatisticService {
    private final PostStatistic postStatistic;
    public StatisticResponseDto getPostStatistic(PostStatisticRequestDto postStatisticRequestDto) {
        return postStatistic.getPostStatistic(postStatisticRequestDto);
    }

    public StatisticResponseDto getCommentStatistic(CommentStatisticRequestDto commentStatisticRequestDto) {
        return postStatistic.getCommentStatistic(commentStatisticRequestDto);
    }
    public StatisticResponseDto getLikeStatistic(LikeStatisticRequestDto likeStatisticRequestDto) {
        return postStatistic.getLikeStatistic(likeStatisticRequestDto);
    }

}
