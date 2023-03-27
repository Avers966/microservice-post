package ru.skillbox.diplom.group35.microservice.post.service.comment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentDto;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentSearchDto;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentType;
import ru.skillbox.diplom.group35.microservice.post.mapper.comment.CommentMapper;
import ru.skillbox.diplom.group35.microservice.post.model.comment.Comment;
import ru.skillbox.diplom.group35.microservice.post.model.comment.Comment_;
import ru.skillbox.diplom.group35.microservice.post.repository.comment.CommentRepository;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static ru.skillbox.diplom.group35.microservice.post.specification.CommentSpecification.equal;
import static ru.skillbox.diplom.group35.microservice.post.specification.CommentSpecification.getBaseSpecification;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public CommentDto createComment(CommentDto commentDto, UUID id) {

        log.info("CreateComment: PostId: {} Comment: {}", id, commentDto);
        commentDto.setPostId(id);
        if (commentDto.getParentId() != null) {
            commentDto.setCommentType(CommentType.COMMENT);
            Optional<Comment> optionalParentComment = commentRepository.findById(commentDto.getParentId());
            Comment parentComment;
            if (optionalParentComment.isPresent()) {
                parentComment = optionalParentComment.get();
                parentComment.setCommentsCount(parentComment.getCommentsCount() + 1);
                commentRepository.save(parentComment);
            }
        }
        Comment comment = commentRepository.save(commentMapper.convertToEntity(commentDto));
        log.info("CreateComment: Comment: {}", commentDto);
        return commentMapper.convertToDto(comment);
    }

    public CommentDto createSubComment(CommentDto commentDto, UUID id, UUID commentId) {

        log.info("CreateSubComment(): PostId: {} CommentId: {} UpdateComment: {}", id, commentId, commentDto);
        Optional<Comment> subCommentOptional = commentRepository.findById(commentId);
        Comment subComment = null;
        if (subCommentOptional.isPresent()) {
            subComment = subCommentOptional.get();
            subComment.setPostId(id);
            subComment.setCommentText(commentDto.getCommentText());
            subComment.setTimeChanged(ZonedDateTime.now());
        }
        log.info("CreateSubComment(): add SubComment: " + subComment);
        assert subComment != null;
        return commentMapper.convertToDto(commentRepository.save(subComment));
    }

    public Page<CommentDto> getComments(UUID id, Pageable page) {
        log.info("GetComments(): CommentId: {} Pageable: {}", id, page);
        Page<Comment> commentPage = commentRepository.findAll(getSpecification(
                                    new CommentSearchDto(id, CommentType.POST)), page);
        log.info("GetComments(): All Comments: " + commentPage);
        return commentPage.map(commentMapper::convertToDto);
    }

    public void deleteComment(UUID id, UUID commentId) {

        log.info("DeleteComment(): PostId: {} CommentId: {}", id, commentId);
        Optional<Comment> optionalParentComment = commentRepository.findById(commentId);
        UUID parentCommentUUID = null;
        Comment parentComment;
        if (optionalParentComment.isPresent()) {
            parentComment = optionalParentComment.get();
            parentCommentUUID = parentComment.getParentId();
        }
        if (parentCommentUUID != null) {
            parentComment = commentRepository.findById(parentCommentUUID).orElseThrow();
            parentComment.setCommentsCount(parentComment.getCommentsCount() - 1);
            commentRepository.save(parentComment);
        }
        commentRepository.delete(commentRepository.findById(commentId).orElseThrow());
    }

    public Page<CommentDto> getSubComments(UUID id, UUID commentId, Pageable page) {

        log.info("GetSubComments: PostId: {} CommentId: {} Pageable: {}", id, commentId, page);
        Page<Comment> commentPage = commentRepository.findAll(getSpecification(
                            new CommentSearchDto(id, commentId, CommentType.COMMENT)), page);
        log.info("GetSubComments: PageOfComments: " + commentPage);
        return commentPage.map(commentMapper::convertToDto);
    }

    private Specification<Comment> getSpecification(CommentSearchDto searchDto) {
        return getBaseSpecification(searchDto)
                        .and(equal(Comment_.postId, searchDto.getPostId(), true)
                        .and(equal(Comment_.parentId, searchDto.getParentId(), true)));
    }
}
