package ru.skillbox.diplom.group35.microservice.post.repository.like;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.post.dto.like.ReactionDto;
import ru.skillbox.diplom.group35.microservice.post.model.like.Like;
import ru.skillbox.diplom.group35.microservice.post.model.like.LikeType;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends BaseRepository<Like> {
    Optional<Like> findByTypeAndItemIdAndAuthorId(LikeType type, UUID itemId, UUID authorId);

    @Query("SELECT new ru.skillbox.diplom.group35.microservice.post.dto.like.ReactionDto(" +
            "l.reactionType, count(l)) FROM Like l " +
            "WHERE l.itemId = :itemId AND l.isDeleted = false " +
            "GROUP BY l.reactionType")
    List<ReactionDto> findReactions(@Param("itemId") UUID itemId);
    List<Like> findAllByTimeBefore(ZonedDateTime time);
}
