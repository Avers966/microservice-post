package ru.skillbox.diplom.group35.microservice.post.repository.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.post.dto.StatisticPerDate;
import ru.skillbox.diplom.group35.microservice.post.model.comment.Comment;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends BaseRepository<Comment> {

    Page<Comment> findAll(Specification<Comment> specification, Pageable page);
    List<Comment> findAllByTimeBefore(ZonedDateTime time);
    @Query("SELECT " + "new ru.skillbox.diplom.group35.microservice.post.dto.StatisticPerDate(" +
            "cast(DATE_TRUNC('month', c.time) as timestamp), cast(count(c.time) as integer)) "
            + "FROM Comment c WHERE c.time >= DATE_TRUNC('month', cast(:firstMonth as timestamp))" +
            "AND c.time < DATE_TRUNC('month', cast(:lastMonth as timestamp)) " +
            "GROUP BY DATE_TRUNC('month', c.time)")
    List<StatisticPerDate> getStatPerMonth(@Param("firstMonth") ZonedDateTime firstMonth,
                                           @Param("lastMonth") ZonedDateTime lastMonth);
    @Query("SELECT " + "new ru.skillbox.diplom.group35.microservice.post.dto.StatisticPerDate( " +
            "cast(DATE_TRUNC('hour', c.time) as timestamp), cast(count(c.time) as integer)) " +
            "FROM Comment c WHERE c.time >= DATE_TRUNC('hour', cast(:startOfDay as timestamp)) " +
            "AND c.time < DATE_TRUNC('hour', cast(:endOfDay as timestamp)) " +
            "GROUP BY DATE_TRUNC('hour', c.time)")
    List<StatisticPerDate> getStatPerHour(@Param("startOfDay") ZonedDateTime firstTime,
                                          @Param("endOfDay") ZonedDateTime lastTime);
}
