package ru.skillbox.diplom.group35.microservice.post.dto.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skillbox.diplom.group35.microservice.post.model.like.ReactionType;

/**
 * Reaction
 *
 * @author Vladimir Polochanin
 */
@Data
@AllArgsConstructor
public class ReactionDto {
    private String reactionType;
    private Long count;
}
