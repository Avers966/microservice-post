package ru.skillbox.diplom.group35.microservice.post.resource.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.skillbox.diplom.group35.microservice.post.dto.statistic.StatisticResponseDto;;
import ru.skillbox.diplom.group35.microservice.post.dto.statistic.PostStatisticRequestDto;

/**
 * PostFriendClient
 *
 * @author Vladimir Polochanin
 */

@FeignClient(value = "PostFeignClient",
//        url = "http://localhost:8080",
        url = "http://microservice-post",
        path = "/api/v1/post")
public interface PostFeignClient {

    @GetMapping("/statistic/post")
    ResponseEntity<StatisticResponseDto> getPostStatistic(PostStatisticRequestDto requestDto);

    @GetMapping("/statistic/comment")
    ResponseEntity<StatisticResponseDto> getCommentStatistic(PostStatisticRequestDto requestDto);
    @GetMapping("/statistic/like")
    ResponseEntity<StatisticResponseDto> getLikeStatistic(PostStatisticRequestDto requestDto);
    @PutMapping("/delayed")
    ResponseEntity<?> getDelayedPost();
}

