package net.dreamfteam.quiznet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableScheduling
@EnableSwagger2
public class QuizNetApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizNetApplication.class, args);
    }

}
