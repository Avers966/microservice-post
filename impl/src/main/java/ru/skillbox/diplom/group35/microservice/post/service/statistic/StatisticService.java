package ru.skillbox.diplom.group35.microservice.post.service.statistic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group35.microservice.post.dto.statistic.StatisticPerDate;
import ru.skillbox.diplom.group35.microservice.post.dto.statistic.PostStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.dto.statistic.StatisticResponseDto;
import ru.skillbox.diplom.group35.microservice.post.mapper.post.PostMapper;
import ru.skillbox.diplom.group35.microservice.post.model.post.*;
import ru.skillbox.diplom.group35.microservice.post.repository.comment.CommentRepository;
import ru.skillbox.diplom.group35.microservice.post.repository.like.LikeRepository;
import ru.skillbox.diplom.group35.microservice.post.repository.post.PostRepository;
import ru.skillbox.diplom.group35.microservice.post.repository.statistic.StatisticRepository;


import java.util.List;
import java.util.stream.Collectors;

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
    private final PostRepository postRepository;
    private final static String HOUR = "hour";
    private final static String MONTH = "month";
    private final StatisticRepository statisticRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final PostMapper postMapper;
    public StatisticResponseDto getPostStatistic(PostStatisticRequestDto requestDto) {
        return getStatistic(requestDto,
                            postRepository.findAllByTypeAndTimeBefore(PostType.POSTED, requestDto.getDate().plusDays(1)).size(),
                            statisticRepository.getPostStatistic(requestDto, MONTH),
                            statisticRepository.getPostStatistic(requestDto, HOUR));
    }

    public StatisticResponseDto getCommentStatistic(PostStatisticRequestDto requestDto) {
        return getStatistic(requestDto,
                            commentRepository.findAllByTimeBefore(requestDto.getDate().plusDays(1)).size(),
                            statisticRepository.getCommentStatistic(requestDto, MONTH),
                            statisticRepository.getCommentStatistic(requestDto, HOUR));
    }
    public StatisticResponseDto getLikeStatistic(PostStatisticRequestDto requestDto) {
        return getStatistic(requestDto,
                            likeRepository.findAllByTimeBefore(requestDto.getDate().plusDays(1)).size(),
                            statisticRepository.getLikeStatistic(requestDto, MONTH),
                            statisticRepository.getLikeStatistic(requestDto, HOUR));
    }

    public StatisticResponseDto getStatistic(PostStatisticRequestDto requestDto, Integer count,
                                             List<StatisticPerDate> monthList,
                                             List<StatisticPerDate> hourList) {
        StatisticResponseDto responseDto = new StatisticResponseDto();
        responseDto.setDate(requestDto.getDate());
        responseDto.setCount(count);
        setCountsPerDate(responseDto, monthList, hourList);
        return responseDto;
    }

    public void setCountsPerDate(StatisticResponseDto responseDto,
                                 List<StatisticPerDate> statisticPerMonthList,
                                 List<StatisticPerDate> statisticPerHourList) {
        responseDto.setCountPerMonth(statisticPerMonthList
                .stream()
                .map(postMapper::convertToStatisticPerDateDto).
                collect(Collectors.toList()));
        responseDto.setCountPerHours(statisticPerHourList
                .stream()
                .map(postMapper::convertToStatisticPerDateDto)
                .collect(Collectors.toList()));
    }
}
