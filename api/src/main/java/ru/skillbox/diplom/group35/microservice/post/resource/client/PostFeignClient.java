package ru.skillbox.diplom.group35.microservice.post.resource.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import ru.skillbox.diplom.group35.microservice.post.dto.StatisticResponseDto;;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostStatisticRequestDto;

/**
 * PostFriendClient
 *
 * @author Vladimir Polochanin
 */

@FeignClient(value = "PostFeignClient",
//        url = "http://localhost:8080",
        url = "http://microservice-post",
        path = "/api/v1/post/statistic")
public interface PostFeignClient {

    @GetMapping("/post")
    ResponseEntity<StatisticResponseDto> getPostStatistic(PostStatisticRequestDto requestDto);

    @GetMapping("/comment")
    ResponseEntity<StatisticResponseDto> getCommentStatistic(PostStatisticRequestDto requestDto);
    @GetMapping("/like")
    ResponseEntity<StatisticResponseDto> getLikeStatistic(PostStatisticRequestDto requestDto);

}

