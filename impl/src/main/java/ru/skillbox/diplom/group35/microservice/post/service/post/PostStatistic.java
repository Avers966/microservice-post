package ru.skillbox.diplom.group35.microservice.post.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skillbox.diplom.group35.microservice.post.dto.StatisticPerDate;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.dto.like.LikeStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.dto.StatisticResponseDto;
import ru.skillbox.diplom.group35.microservice.post.mapper.post.PostMapper;
import ru.skillbox.diplom.group35.microservice.post.model.post.PostType;
import ru.skillbox.diplom.group35.microservice.post.repository.comment.CommentRepository;
import ru.skillbox.diplom.group35.microservice.post.repository.like.LikeRepository;
import ru.skillbox.diplom.group35.microservice.post.repository.post.PostRepository;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PostStatistic
 *
 * @author Vladimir Polochanin
 */

@Component
@RequiredArgsConstructor
public class PostStatistic {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final PostMapper postMapper;


    public StatisticResponseDto getPostStatistic(PostStatisticRequestDto requestDto) {
        StatisticResponseDto responseDto = new StatisticResponseDto();
        responseDto.setDate(requestDto.getDate());
        responseDto.setCount(postRepository
                    .findAllByTypeAndTimeBefore(PostType.POSTED, requestDto.getDate()).size());
        List<StatisticPerDate> statisticPerMonthList = postRepository.getStatPerMonth(
                        requestDto.getFirstMonth(), requestDto.getLastMonth().plusMonths(1));
        List<StatisticPerDate> statisticPerHourList = postRepository.getStatPerHour(
                        requestDto.getDate().with(ChronoField.HOUR_OF_DAY, 0),
                        requestDto.getDate().with(ChronoField.HOUR_OF_DAY, 23));
        setCountsPerDate(responseDto, statisticPerMonthList, statisticPerHourList);
        return responseDto;
    }

    public StatisticResponseDto getCommentStatistic(CommentStatisticRequestDto requestDto) {
        StatisticResponseDto responseDto = new StatisticResponseDto();
        responseDto.setDate(requestDto.getDate());
        responseDto.setCount(commentRepository.findAllByTimeBefore(requestDto.getDate()).size());
        List<StatisticPerDate> statisticPerMonthList = commentRepository.getStatPerMonth(
                requestDto.getFirstMonth(), requestDto.getLastMonth().plusMonths(1));
        List<StatisticPerDate> statisticPerHourList = commentRepository.getStatPerHour(
                requestDto.getDate().with(ChronoField.HOUR_OF_DAY, 0),
                requestDto.getDate().with(ChronoField.HOUR_OF_DAY, 23));
        setCountsPerDate(responseDto, statisticPerMonthList, statisticPerHourList);
        return responseDto;
    }

    public StatisticResponseDto getLikeStatistic(LikeStatisticRequestDto requestDto) {
        StatisticResponseDto responseDto = new StatisticResponseDto();
        responseDto.setDate(requestDto.getDate());
        responseDto.setCount(likeRepository.findAllByTimeBefore(requestDto.getDate()).size());
        List<StatisticPerDate> statisticPerMonthList = likeRepository.getStatPerMonth(
                requestDto.getFirstMonth(), requestDto.getLastMonth().plusMonths(1));
        List<StatisticPerDate> statisticPerHourList = likeRepository.getStatPerHour(
                requestDto.getDate().with(ChronoField.HOUR_OF_DAY, 0),
                requestDto.getDate().with(ChronoField.HOUR_OF_DAY, 23));
        setCountsPerDate(responseDto, statisticPerMonthList, statisticPerHourList);
        return responseDto;
    }

    public void setCountsPerDate(StatisticResponseDto responseDto, List<StatisticPerDate> statisticPerMonthList,
                                                                    List<StatisticPerDate> statisticPerHourList) {
        responseDto.setCountPerMonth(statisticPerMonthList
                .stream().map(postMapper::convertToStatisticPerDateDto).collect(Collectors.toList()));
        responseDto.setCountPerHours(statisticPerHourList
                .stream().map(postMapper::convertToStatisticPerDateDto).collect(Collectors.toList()));
    }
}
