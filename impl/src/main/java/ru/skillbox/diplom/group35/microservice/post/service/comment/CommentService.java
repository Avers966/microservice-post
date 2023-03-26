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

    public CommentDto createComment(CommentDto commentDto, UUID uuid) {

        log.info("CreateComment: PostUUID: {} Comment: {}", uuid, commentDto);
        commentDto.setPostId(uuid);
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

    public CommentDto createSubComment(CommentDto commentDto, UUID uuid, UUID commentUUID) {

        log.info("CreateSubComment(): PostUUID: {} CommentUUID: {} UpdateComment: {}", uuid, commentUUID, commentDto);
        Optional<Comment> subCommentOptional = commentRepository.findById(commentUUID);
        Comment subComment = null;
        if (subCommentOptional.isPresent()) {
            subComment = subCommentOptional.get();
            subComment.setPostId(uuid);
            subComment.setCommentText(commentDto.getCommentText());
            subComment.setTimeChanged(ZonedDateTime.now());
        }
        log.info("CreateSubComment(): add SubComment: " + subComment);
        assert subComment != null;
        return commentMapper.convertToDto(commentRepository.save(subComment));
    }

    public Page<CommentDto> getComments(UUID uuid, Pageable page) {
        log.info("GetComments(): CommentUUID: {} Pageable: {}", uuid, page);
        Page<Comment> commentPage = commentRepository.findAll(getSpecification(
                                    new CommentSearchDto(uuid, CommentType.POST)), page);
        log.info("GetComments(): All Comments: " + commentPage);
        return commentPage.map(commentMapper::convertToDto);
    }

    public void deleteComment(UUID uuid, UUID commentUUID) {

        log.info("DeleteComment(): PostUUID: {} CommentUUID: {}", uuid, commentUUID);
        Optional<Comment> optionalParentComment = commentRepository.findById(commentUUID);
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
        commentRepository.delete(commentRepository.findById(commentUUID).orElseThrow());
    }

    public Page<CommentDto> getSubComments(UUID uuid, UUID commentUUID, Pageable page) {

        log.info("GetSubComments: PostUUID: {} CommentUUID: {} Pageable: {}", uuid, commentUUID, page);
        Page<Comment> commentPage = commentRepository.findAll(getSpecification(
                            new CommentSearchDto(uuid, commentUUID, CommentType.COMMENT)), page);
        log.info("GetSubComments: PageOfComments: " + commentPage);
        return commentPage.map(commentMapper::convertToDto);
    }

    private Specification<Comment> getSpecification(CommentSearchDto searchDto) {
        return getBaseSpecification(searchDto)
                        .and(equal(Comment_.postId, searchDto.getPostId(), true)
                        .and(equal(Comment_.parentId, searchDto.getParentId(), true)));
    }
}
