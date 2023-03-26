package ru.skillbox.diplom.group35.microservice.post.repository.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.post.model.comment.Comment;

@Repository
public interface CommentRepository extends BaseRepository<Comment> {

    Page<Comment> findAll(Specification<Comment> specification, Pageable page);
}
