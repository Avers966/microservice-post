package ru.skillbox.diplom.group35.microservice.post.resource.post;

import com.nimbusds.jose.util.JSONObjectUtils;
import java.text.ParseException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.skillbox.diplom.group35.library.core.annotation.EnableExceptionHandler;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentDto;
import ru.skillbox.diplom.group35.microservice.post.dto.comment.CommentSearchDto;
import ru.skillbox.diplom.group35.microservice.post.dto.like.LikeDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostDto;
import ru.skillbox.diplom.group35.microservice.post.dto.post.PostSearchDto;
import ru.skillbox.diplom.group35.microservice.post.model.like.LikeType;
import ru.skillbox.diplom.group35.microservice.post.service.comment.CommentService;
import ru.skillbox.diplom.group35.microservice.post.service.like.LikeService;
import ru.skillbox.diplom.group35.microservice.post.service.post.PostService;

/**
 * PostResourceImpl
 *
 * @author Marat Safagareev
 */

@Slf4j
@RestController
@EnableExceptionHandler
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {

  private final CommentService commentService;
  private final LikeService likeService;
  private final PostService postService;

  public static UUID getUserId() {
    UUID id = null;
    Base64.Decoder decoder = Base64.getUrlDecoder();
    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
        RequestContextHolder.getRequestAttributes())).getRequest();
    String token = request.getHeader("Authorization").replace("Bearer ", "");
    try {
      Map<String, Object> payload = JSONObjectUtils.parse(
          new String(decoder.decode(token.split("\\.")[1])));
      id = UUID.fromString(payload.get("id").toString());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return id;
  }

  @Override
  public ResponseEntity<Page<PostDto>> getAll(PostSearchDto postSearchDto, Pageable pageable) {
    log.info("getAll(): postSearchDto:{}, pageable:{}", postSearchDto, pageable);
    return ResponseEntity.ok(postService.getAll(postSearchDto, pageable));
  }

  @Override
  public ResponseEntity<PostDto> getById(UUID id) {
    log.info("getById(): id :{}", id);
    return ResponseEntity.ok(postService.getById(id));
  }

  @Override
  public ResponseEntity<PostDto> create(PostDto postDto) {
    log.info("create(): postDto:{}", postDto);
    return ResponseEntity.ok(postService.create(postDto));
  }

  @Override
  public ResponseEntity<PostDto> update(PostDto postDto) {
    log.info("update():  postDto:{}", postDto);
    return ResponseEntity.ok(postService.update(postDto));
  }

  @Override
  public void deleteById(UUID id) {
    log.info("deleteById(): id :{}", id);
    postService.deleteById(id);
  }

  @Override
  public ResponseEntity<CommentDto> createSubComment(CommentDto commentDto, UUID id,
      UUID commentId) {
    return ResponseEntity.ok(commentService.createSubComment(commentDto, id, commentId));
  }

  @Override
  public ResponseEntity<CommentDto> deleteComment(UUID id, UUID commentId) {
    commentService.deleteComment(id, commentId);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Page<CommentDto>> getComment(UUID postId, CommentSearchDto searchDto, Pageable page) {
    return ResponseEntity.ok(commentService.getComments(postId, searchDto, page));
  }

  @Override
  public ResponseEntity<CommentDto> createComment(UUID id, CommentDto commentDto) {
    return ResponseEntity.ok(commentService.createComment(commentDto, id));
  }

  @Override
  public ResponseEntity<Page<CommentDto>> getSubComment(UUID postId, UUID commentId, CommentSearchDto searchDto, Pageable page) {
    return ResponseEntity.ok(commentService.getSubComments(postId, commentId, searchDto, page));
  }

  @Override
  public ResponseEntity<LikeDto> createPostLike(UUID id) {
    return ResponseEntity.ok(likeService.createLike(id, LikeType.POST));
  }
  @Override
  public ResponseEntity<CommentDto> updateComment(UUID id, CommentDto commentDto) {
    return ResponseEntity.ok(commentService.updateComment(commentDto, id));
  }

  @Override
  public ResponseEntity<LikeDto> deletePostLike(UUID id) {
    likeService.deleteLike(id, LikeType.POST);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<LikeDto> createCommentLike(UUID id, UUID commentId) {
    return ResponseEntity.ok(likeService.createLike(commentId, LikeType.COMMENT));
  }

  @Override
  public ResponseEntity<LikeDto> deleteCommentLike(UUID id, UUID commentId) {
    likeService.deleteLike(commentId, LikeType.COMMENT);
    return ResponseEntity.ok().build();
  }

}
