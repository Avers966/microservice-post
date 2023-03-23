package ru.skillbox.diplom.group35.microservice.post.model.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostType {
  POSTED("POSTED"),
  QUEUED("QUEUED");

  private final String type;
}
