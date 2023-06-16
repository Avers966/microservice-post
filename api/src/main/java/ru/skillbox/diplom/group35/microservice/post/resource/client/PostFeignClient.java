package ru.skillbox.diplom.group35.microservice.post.resource.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * DelayedPostFeignClient
 *
 * @author Vladimir Polochanin
 */
@FeignClient(value = "PostFeignClient",
//        url = "http://localhost:8080",
        url = "http://microservice-post",
        path = "/api/v1/post")
public interface PostFeignClient {

    @PutMapping("/delayed")
    ResponseEntity<?> getDelayedPost();
}
