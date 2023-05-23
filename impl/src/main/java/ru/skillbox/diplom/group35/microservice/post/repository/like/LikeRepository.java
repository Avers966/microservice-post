package ru.skillbox.diplom.group35.microservice.post.repository.like;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.post.dto.StatisticPerDate;
import ru.skillbox.diplom.group35.microservice.post.model.like.Like;
import ru.skillbox.diplom.group35.microservice.post.model.like.LikeType;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends BaseRepository<Like> {
    Optional<Like> findByTypeAndItemIdAndAuthorId(LikeType type, UUID itemId, UUID authorId);
    List<Like> findAllByTimeBefore(ZonedDateTime time);
    @Query("SELECT " + "new ru.skillbox.diplom.group35.microservice.post.dto.StatisticPerDate(" +
            "cast(DATE_TRUNC('month', l.time) as timestamp), cast(count(l.time) as integer)) "
            + "FROM Like l WHERE l.time >= DATE_TRUNC('month', cast(:firstMonth as timestamp))" +
            "AND l.time < DATE_TRUNC('month', cast(:lastMonth as timestamp)) " +
            "GROUP BY DATE_TRUNC('month', l.time)")
    List<StatisticPerDate> getStatPerMonth(@Param("firstMonth") ZonedDateTime firstMonth,
                                           @Param("lastMonth") ZonedDateTime lastMonth);
    @Query("SELECT " + "new ru.skillbox.diplom.group35.microservice.post.dto.StatisticPerDate( " +
            "cast(DATE_TRUNC('hour', l.time) as timestamp), cast(count(l.time) as integer)) " +
            "FROM Like l WHERE l.time >= DATE_TRUNC('hour', cast(:startOfDay as timestamp)) " +
            "AND l.time < DATE_TRUNC('hour', cast(:endOfDay as timestamp)) " +
            "GROUP BY DATE_TRUNC('hour', l.time)")
    List<StatisticPerDate> getStatPerHour(@Param("startOfDay") ZonedDateTime firstTime,
                                          @Param("endOfDay") ZonedDateTime lastTime);
}
