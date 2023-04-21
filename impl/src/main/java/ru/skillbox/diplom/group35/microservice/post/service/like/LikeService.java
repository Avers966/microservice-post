package ru.skillbox.diplom.group35.microservice.post.service.like;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group35.microservice.post.dto.like.LikeDto;
import ru.skillbox.diplom.group35.microservice.post.mapper.like.LikeMapper;
import ru.skillbox.diplom.group35.microservice.post.model.comment.Comment;
import ru.skillbox.diplom.group35.microservice.post.model.like.Like;
import ru.skillbox.diplom.group35.microservice.post.model.like.LikeType;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;
import ru.skillbox.diplom.group35.microservice.post.repository.comment.CommentRepository;
import ru.skillbox.diplom.group35.microservice.post.repository.like.LikeRepository;
import ru.skillbox.diplom.group35.microservice.post.repository.post.PostRepository;
import ru.skillbox.diplom.group35.microservice.post.resource.post.PostControllerImpl;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

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

    public LikeDto createLike(UUID itemId, LikeType likeType) {

        log.info("ItemId in createLike(LikeService): " + itemId);
        Optional<Like> optionalLike = likeRepository.findByTypeAndItemId(likeType, itemId);
        Like like;
        if (optionalLike.isEmpty()) {
            like = new Like();
            like.setAuthorId(PostControllerImpl.getUserId());
            like.setType(likeType);
            like.setItemId(itemId);
            like.setIsDeleted(false);
            like.setTime(ZonedDateTime.now());
            likeAmount(itemId, likeType, 1);
        } else {
            like = optionalLike.get();
        }
        log.info("Like in createLike(LikeService): " + like);
        return likeMapper.convertToDto(likeRepository.save(like));
    }

    public void deleteLike(UUID itemId, LikeType likeType) {

        log.info("ItemId in deleteLike(LikeService): " + itemId);
        Optional<Like> optionalLike = likeRepository.findByTypeAndItemId(likeType, itemId);
        if(optionalLike.isPresent()) {
            Like like = optionalLike.get();
            likeAmount(itemId, likeType, -1);
            likeRepository.deleteById(like.getId());
        }
    }

    private void likeAmount(UUID itemId, LikeType type, int one) {

        if (type.equals(LikeType.POST)) {
                Optional<Post> optionalPost = postRepository.findById(itemId);
                if (optionalPost.isPresent()) {
                    Post post = optionalPost.get();
                    post.setLikeAmount(post.getLikeAmount() + one);
                    postRepository.save(post);
                    log.info("LikeService in changeLikeAmount: likeAmount changed for " + type + " id - " + post.getId());
                }
        } else {
                Optional<Comment> optionalComment = commentRepository.findById(itemId);
                if (optionalComment.isPresent()) {
                    Comment comment = optionalComment.get();
                    comment.setLikeAmount(comment.getLikeAmount() + one);
                    commentRepository.save(comment);
                    log.info("LikeService in changeLikeAmount: likeAmount changed for " + type + " id - " + comment.getId());
                }
            }
        }
}
