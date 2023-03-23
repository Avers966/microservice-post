package ru.skillbox.diplom.group35.microservice.post.repository.post;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;

@Repository
public interface PostRepository extends BaseRepository<Post> {

}
