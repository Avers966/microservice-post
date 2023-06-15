package ru.skillbox.diplom.group35.microservice.post.repository.statistic;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.microservice.post.dto.statistic.StatisticPerDate;
import ru.skillbox.diplom.group35.microservice.post.dto.statistic.PostStatisticRequestDto;
import ru.skillbox.diplom.group35.microservice.post.model.comment.Comment;
import ru.skillbox.diplom.group35.microservice.post.model.comment.Comment_;
import ru.skillbox.diplom.group35.microservice.post.model.like.Like;
import ru.skillbox.diplom.group35.microservice.post.model.like.Like_;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post_;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PostStatistic
 *
 * @author Vladimir Polochanin
 */
@Repository
@AllArgsConstructor
public class StatisticRepository {
    EntityManager em;

    public List<StatisticPerDate> getPostStatistic(PostStatisticRequestDto requestDto, String parameter) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<Post> root = query.from(Post.class);
        Expression<Date> date = builder.function("date_trunc", Date.class, builder.literal(parameter), root.get(Post_.TIME));
        Expression<Long> count = builder.count(root);
        Predicate betweenDate = builder.between(root.get(Post_.TIME), requestDto.getFirstMonth(), requestDto.getLastMonth());
        return getStats(query, date, count, betweenDate);
    }

    public List<StatisticPerDate> getCommentStatistic(PostStatisticRequestDto requestDto, String parameter) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<Comment> root = query.from(Comment.class);
        Expression<Date> date = builder.function("date_trunc", Date.class, builder.literal(parameter), root.get(Comment_.TIME));
        Expression<Long> count = builder.count(root);
        Predicate betweenDate = builder.between(root.get(Comment_.TIME), requestDto.getFirstMonth(), requestDto.getLastMonth());
        return getStats(query, date, count, betweenDate);
    }

    public List<StatisticPerDate> getLikeStatistic(PostStatisticRequestDto requestDto, String parameter) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<Like> root = query.from(Like.class);
        Expression<Date> date = builder.function("date_trunc", Date.class, builder.literal(parameter), root.get(Like_.TIME));
        Expression<Long> count = builder.count(root);
        Predicate betweenDate = builder.between(root.get(Like_.TIME), requestDto.getFirstMonth(), requestDto.getLastMonth());
        return getStats(query, date, count, betweenDate);
    }

    public List<StatisticPerDate> getStats(CriteriaQuery<Tuple> query, Expression<Date> date,
                                           Expression<Long> count, Predicate betweenDate) {
        List<StatisticPerDate> stats = new ArrayList<>();
        List<Tuple> tuples = em.createQuery(query.multiselect(date, count).where(betweenDate).groupBy(date)).getResultList();
        tuples.forEach(tuple -> stats.add(new StatisticPerDate(tuple.get(date), Math.toIntExact(tuple.get(count)))));
        return stats;
    }
}
