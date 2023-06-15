package ru.skillbox.diplom.group35.microservice.post.repository.tag;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group35.library.core.repository.BaseRepository;
import ru.skillbox.diplom.group35.microservice.post.model.tag.Tag;

/**
 * TagRepository
 *
 * @author Marat Safagareev
 */
@Repository
public interface TagRepository extends BaseRepository<Tag> {

  @Query("SELECT t FROM Tag t WHERE t.name LIKE ?1% GROUP BY t.id ORDER BY size(t.posts) desc")
  List<Tag> findAdviceTags(String name, Pageable pageable);
}
