package ru.skillbox.diplom.group35.microservice.post.service.like;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group35.library.core.dto.streaming.EventNotificationDto;
import ru.skillbox.diplom.group35.library.core.utils.SecurityUtil;
import ru.skillbox.diplom.group35.microservice.notification.dto.NotificationType;
import ru.skillbox.diplom.group35.microservice.post.dto.like.LikeDto;
import ru.skillbox.diplom.group35.microservice.post.mapper.like.LikeMapper;
import ru.skillbox.diplom.group35.microservice.post.model.comment.Comment;
import ru.skillbox.diplom.group35.microservice.post.model.like.Like;
import ru.skillbox.diplom.group35.microservice.post.model.like.LikeType;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;
import ru.skillbox.diplom.group35.microservice.post.repository.comment.CommentRepository;
import ru.skillbox.diplom.group35.microservice.post.repository.like.LikeRepository;
import ru.skillbox.diplom.group35.microservice.post.repository.post.PostRepository;
import ru.skillbox.diplom.group35.microservice.post.service.post.PostService;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * LikeService
 *
 * @author Marat Safagareev
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeMapper likeMapper;

    private final SecurityUtil securityUtil;
    private final PostService postService;
    private final KafkaTemplate<String, EventNotificationDto> kafkaTemplate;
    public LikeDto createLike(UUID itemId, LikeDto likeDto, LikeType likeType) {

        UUID userId = securityUtil.getAccountDetails().getId();
        Like like = likeRepository.findByTypeAndItemIdAndAuthorId(likeType, itemId, userId).orElse(new Like());
        likeDto.setItemId(itemId);
        if (like.getId() == null) {
            like = likeMapper.convertToEntity(likeDto);
            like.setAuthorId(userId);
            like.setType(likeType);
            likeAmount(itemId, likeType, 1);
        } else {
            like.setReactionType(likeDto.getReactionType());
            like.setTime(ZonedDateTime.now());
            if (like.getIsDeleted()) {
                like.setIsDeleted(false);
                likeAmount(itemId, likeType, 1);
            }
        }
        createAndSendNotification(like, NotificationType.LIKE);
        return likeMapper.convertToDto(likeRepository.save(like));
    }

    public void createAndSendNotification(Like like, NotificationType type) {
        EventNotificationDto notification = new EventNotificationDto();
        notification.setAuthorId(like.getAuthorId());
        notification.setReceiverId(like.getType() == LikeType.POST ? postService.getById(like.getItemId()).getAuthorId() :
                                                                    commentRepository.getById(like.getItemId()).getAuthorId());
        notification.setNotificationType(String.valueOf(type));
        notification.setContent(like.getType() == LikeType.POST ? "понравился ваш пост" : "понравился ваш комментарий");
        kafkaTemplate.send("event", "event", notification);
    }

    public void deleteLike(UUID itemId, LikeType likeType) {
        Like like = likeRepository.findByTypeAndItemIdAndAuthorId(likeType, itemId, securityUtil.getAccountDetails().getId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                        "Like not found for this " + likeType.toString().toLowerCase() +  "Id :: " + itemId));
        if (like.getId() != null || !like.getIsDeleted()) {
            like.setReactionType(null);
            likeAmount(itemId, likeType, -1);
            likeRepository.deleteById(like.getId());
        }
    }

    private void likeAmount(UUID itemId, LikeType type, int one) {
        if (type.equals(LikeType.POST)) {
            Post post = postRepository.findById(itemId).orElseThrow();
            post.setLikeAmount(post.getLikeAmount() + one);
            postRepository.save(post);
            log.info("LikeService in changeLikeAmount: likeAmount changed for " + type + " id - " + post.getId());
        } else {
            Comment comment = commentRepository.findById(itemId).orElseThrow();
            comment.setLikeAmount(comment.getLikeAmount() + one);
            commentRepository.save(comment);
            log.info("LikeService in changeLikeAmount: likeAmount changed for " + type + " id - " + comment.getId());
        }
    }
}
