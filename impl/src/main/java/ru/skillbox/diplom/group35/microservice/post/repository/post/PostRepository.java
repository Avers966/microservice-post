package ru.skillbox.diplom.group35.microservice.post.repository.post;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.post.dto.StatisticPerDate;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;
import ru.skillbox.diplom.group35.microservice.post.model.post.PostType;

@Repository
public interface PostRepository extends BaseRepository<Post> {

  List<Post> findAllByTypeAndPublishDateBefore(PostType postType, ZonedDateTime time);

  List<Post> findAllByTypeAndTimeBefore(PostType postType, ZonedDateTime time);
  @Query("SELECT " + "new ru.skillbox.diplom.group35.microservice.post.dto.StatisticPerDate(" +
          "cast(DATE_TRUNC('month', p.time) as timestamp), cast(count(p.time) as integer)) "
          + "FROM Post p WHERE p.time >= DATE_TRUNC('month', cast(:firstMonth as timestamp))" +
                          "AND p.time < DATE_TRUNC('month', cast(:lastMonth as timestamp)) " +
                          "GROUP BY DATE_TRUNC('month', p.time)")
  List<StatisticPerDate> getStatPerMonth(@Param("firstMonth") ZonedDateTime firstMonth,
                                         @Param("lastMonth") ZonedDateTime lastMonth);
  @Query("SELECT " + "new ru.skillbox.diplom.group35.microservice.post.dto.StatisticPerDate( " +
                      "cast(DATE_TRUNC('hour', p.time) as timestamp), cast(count(p.time) as integer)) " +
          "FROM Post p WHERE p.time >= DATE_TRUNC('hour', cast(:startOfDay as timestamp)) " +
                      "AND p.time < DATE_TRUNC('hour', cast(:endOfDay as timestamp)) " +
                      "GROUP BY DATE_TRUNC('hour', p.time)")
  List<StatisticPerDate> getStatPerHour(@Param("startOfDay") ZonedDateTime firstTime,
                                         @Param("endOfDay") ZonedDateTime lastTime);
}
