package ru.skillbox.diplom.group35.microservice.post.service.post;

import java.time.ZonedDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;
import ru.skillbox.diplom.group35.microservice.post.model.post.PostType;
import ru.skillbox.diplom.group35.microservice.post.repository.post.PostRepository;

/**
 * DelayedPostService
 *
 * @author Marat Safagareev
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DelayedPostService {

  private final PostRepository postRepository;

  @Scheduled(cron = "0 * * * * *")
  protected void publishPost() {
    List<Post> postsToPublish = postRepository
        .findAllByTypeAndPublishDateBefore(PostType.QUEUED, ZonedDateTime.now());
    log.info("Checking database for queued posts, " + postsToPublish.size() + " posts found.");
    postsToPublish.forEach(post -> {
      log.info("publishPost(): post:{}", post);
      post.setTime(post.getPublishDate());
      post.setPublishDate(null);
      post.setType(PostType.POSTED);
      postRepository.save(post);
    });
  }
}
