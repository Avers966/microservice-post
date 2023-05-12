package ru.skillbox.diplom.group35.microservice.post.repository.like;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.post.model.like.Like;
import ru.skillbox.diplom.group35.microservice.post.model.like.LikeType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends BaseRepository<Like> {
    Optional<Like> findByTypeAndItemIdAndAuthorId(LikeType type, UUID itemId, UUID authorId);
}
