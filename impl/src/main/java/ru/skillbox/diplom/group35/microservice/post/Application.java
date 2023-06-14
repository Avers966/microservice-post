package ru.skillbox.diplom.group35.microservice.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.skillbox.diplom.group35.library.core.annotation.*;

/**
 * Application
 *
 * @author Marat Safagareev
 */
@JwtProvider
@EnableSwagger
@EnableSecurity
@EnableOpenFeign
@EnableScheduling
@EnableBaseRepository
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
