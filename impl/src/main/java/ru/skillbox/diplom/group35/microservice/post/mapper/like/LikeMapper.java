package ru.skillbox.diplom.group35.microservice.post.mapper.like;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group35.microservice.post.dto.like.LikeDto;
import ru.skillbox.diplom.group35.microservice.post.model.like.Like;

/**
 * LikeMapper
 *
 * @author Marat Safagareev
 */

@Mapper
public interface LikeMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "isDeleted", source = "isDeleted")
    @Mapping(target = "time", source = "time")
    @Mapping(target = "itemId", source = "itemId")
    @Mapping(target = "authorId", source = "authorId")
    LikeDto convertToDto(Like like);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "time", source = "time")
    @Mapping(target = "itemId", source = "itemId")
    @Mapping(target = "authorId", source = "authorId")
    Like convertToEntity(LikeDto likeDto);
}
