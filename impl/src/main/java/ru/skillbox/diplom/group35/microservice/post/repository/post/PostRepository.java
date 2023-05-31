package ru.skillbox.diplom.group35.microservice.post.repository.post;

import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;
import ru.skillbox.diplom.group35.microservice.post.model.post.PostType;

@Repository
public interface PostRepository extends BaseRepository<Post> {
  List<Post> findAllByTypeAndPublishDateBefore(PostType postType, ZonedDateTime time);

  List<Post> findAllByTypeAndTimeBefore(PostType postType, ZonedDateTime time);
}
