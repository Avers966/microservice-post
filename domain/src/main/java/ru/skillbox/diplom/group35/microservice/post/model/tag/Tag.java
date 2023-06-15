package ru.skillbox.diplom.group35.microservice.post.model.tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group35.library.core.model.base.BaseEntity;
import ru.skillbox.diplom.group35.microservice.post.model.post.Post;

/**
 * Tag
 *
 * @author Marat Safagareev
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonIgnoreProperties(value = {"posts"})
public class Tag extends BaseEntity {

  @Column(name = "name", columnDefinition = "varchar(255)", nullable = false)
  private String name;

  @ManyToMany(mappedBy = "tags")
  private Set<Post> posts;
}
