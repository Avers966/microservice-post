package ru.skillbox.diplom.group35.microservice.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import ru.skillbox.diplom.group35.library.core.annotation.EnableBaseRepository;

/**
 * Application
 *
 * @author Marat Safagareev
 */
@EnableBaseRepository
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
    ManagementWebSecurityAutoConfiguration.class})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
