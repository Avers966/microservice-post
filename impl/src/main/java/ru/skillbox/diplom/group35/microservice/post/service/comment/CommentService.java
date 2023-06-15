package ru.skillbox.diplom.group35.microservice.post.service.comment;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group35.library.core.dto.streaming.EventNotificationDto;
import ru.skillbox.diplom.group35.library.core.utils.SecurityUtil;
import ru.skillbox.diplom.group35.microservice.notification.dto.NotificationType;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentDto;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentSearchDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.mapper.comment.CommentMapper;
import ru.skillbox.diplom.group35.microservice.post.model.comment.Comment;
import ru.skillbox.diplom.group35.microservice.post.model.comment.CommentType;
import ru.skillbox.diplom.group35.microservice.post.model.comment.Comment_;
import ru.skillbox.diplom.group35.microservice.post.repository.comment.CommentRepository;
import ru.skillbox.diplom.group35.microservice.post.service.post.PostService;

import java.time.ZonedDateTime;
import java.util.UUID;

import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.equal;
import static ru.skillbox.diplom.group35.library.core.utils.SpecificationUtil.getBaseSpecification;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostService postService;

    private final SecurityUtil securityUtil;
    private final KafkaTemplate<String, EventNotificationDto> kafkaTemplate;

    public CommentDto createComment(CommentDto commentDto, UUID id) {
        commentDto.setPostId(id);
        if (commentDto.getParentId() != null) {
            Comment parentComment = commentRepository.findById(commentDto.getParentId()).orElseThrow();
            commentDescription(commentDto, CommentType.COMMENT);
            parentComment.setCommentsCount(parentComment.getCommentsCount() + 1);
            commentRepository.save(parentComment);
        } else {
            PostDto postDto = postService.getById(id);
            postDto.setCommentsCount(postDto.getCommentsCount() + 1);
            postService.update(postDto);
            commentDescription(commentDto, CommentType.POST);
        }
        Comment comment = commentRepository.save(commentMapper.convertToEntity(commentDto));
        createAndSendNotification(comment, NotificationType.POST_COMMENT);
        return commentMapper.convertToDto(comment);
    }

    public CommentDto updateComment(CommentDto commentDto, UUID id) {
        Comment comment = commentRepository.findById(commentDto.getId()).orElseThrow();
        comment.setCommentText(commentDto.getCommentText());
        comment.setPostId(id);
        comment.setTimeChanged(ZonedDateTime.now());
        return commentMapper.convertToDto(commentRepository.save(comment));
    }

    public void createAndSendNotification(Comment comment, NotificationType type) {
        EventNotificationDto notification = new EventNotificationDto();
        notification.setAuthorId(comment.getAuthorId());
        notification.setReceiverId(postService.getById(comment.getPostId()).getAuthorId());
        notification.setNotificationType(String.valueOf(type));
        notification.setContent(comment.getCommentText().length() < 20 ?
                comment.getCommentText() : comment.getCommentText().substring(0, 25) + "...");
        kafkaTemplate.send("event", "event", notification);
    }

    public CommentDto createSubComment(CommentDto commentDto, UUID id, UUID commentId) {
        Comment subComment = commentRepository.findById(commentId).orElseThrow();
        subComment.setPostId(id);
        subComment.setCommentText(commentDto.getCommentText());
        subComment.setTimeChanged(ZonedDateTime.now());
        createAndSendNotification(subComment, NotificationType.COMMENT_COMMENT);
        return commentMapper.convertToDto(commentRepository.save(subComment));
    }

    public Page<CommentDto> getComments(UUID postId, CommentSearchDto searchDto, Pageable page) {
        searchDto.setCommentType(CommentType.POST);
        Page<Comment> commentPage = commentRepository.findAll(getSpecification(searchDto), page);
        return commentPage.map(commentMapper::convertToDto);
    }

    public void deleteComment(UUID id, UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                                            .orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id :: " + commentId));
        if (comment.getParentId() != null) {
            Comment parentComment = commentRepository.findById(comment.getParentId())
                                                    .orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id :: " + commentId));
            parentComment.setCommentsCount(parentComment.getCommentsCount() - 1);
            commentRepository.save(parentComment);
        } else {
            PostDto postDto = postService.getById(id);
            postDto.setCommentsCount(postDto.getCommentsCount() - 1);
            postService.update(postDto);
        }
        commentRepository.deleteById(commentId);
    }

    public Page<CommentDto> getSubComments(UUID postId, UUID commentId, CommentSearchDto searchDto, Pageable page) {
        searchDto.setParentId(commentId);
        searchDto.setCommentType(CommentType.COMMENT);
        Page<Comment> commentPage = commentRepository.findAll(getSpecification(searchDto), page);
        log.info("GetSubComments: PageOfComments: " + commentPage);
        return commentPage.map(commentMapper::convertToDto);
    }

    private Specification<Comment> getSpecification(CommentSearchDto searchDto) {
        return getBaseSpecification(searchDto)
                        .and(equal(Comment_.postId, searchDto.getPostId(), true)
                        .and(equal(Comment_.commentType, searchDto.getCommentType(), true))
                        .and(equal(Comment_.parentId, searchDto.getParentId(), true)));
    }

    public void commentDescription (CommentDto commentDto, CommentType commentType) {
        commentDto.setAuthorId(securityUtil.getAccountDetails().getId());
        commentDto.setTime(ZonedDateTime.now());
        commentDto.setCommentType(commentType);
    }
}
