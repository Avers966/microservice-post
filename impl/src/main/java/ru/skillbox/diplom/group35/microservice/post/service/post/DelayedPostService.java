package ru.skillbox.diplom.group35.microservice.post.service.post;

import java.time.ZonedDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.library.core.dto.streaming.EventNotificationDto;
import ru.skillbox.diplom.group35.microservice.notification.dto.NotificationType;
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

  private final KafkaTemplate<String, EventNotificationDto> kafkaTemplate;

  @Scheduled(cron = "0 * * * * *")
  protected void publishPost() {
    List<Post> postsToPublish = postRepository
        .findAllByTypeAndPublishDateBefore(PostType.QUEUED, ZonedDateTime.now());
    postsToPublish.forEach(post -> {
      post.setTime(post.getPublishDate());
      post.setPublishDate(null);
      post.setType(PostType.POSTED);
      createAndSendNotification(post, NotificationType.POST);
      postRepository.save(post);
    });
  }

  public void createAndSendNotification(Post post, NotificationType type) {
    EventNotificationDto notification = new EventNotificationDto();
    notification.setAuthorId(post.getAuthorId());
    notification.setReceiverId(null);
    notification.setNotificationType(String.valueOf(NotificationType.POST));
    notification.setContent(post.getTitle());
    kafkaTemplate.send("event", "event", notification);
  }
}
