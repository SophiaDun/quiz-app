package app.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app.demo.domain.QuizUser;
import app.demo.domain.Score;
import app.demo.domain.QuizUserRepository;
import app.demo.domain.ScoreRepository;

import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    	public CommandLineRunner demo(QuizUserRepository userRepository, ScoreRepository scoreRepository) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return (args) -> {
            // Creating sample users
			QuizUser user1 = new QuizUser("user1", passwordEncoder.encode("password1"));
            QuizUser user2 = new QuizUser("user2", passwordEncoder.encode("password2"));
            QuizUser user3 = new QuizUser("user3", passwordEncoder.encode("password3"));

            // Saving users to the database
            userRepository.saveAll(Arrays.asList(user1, user2, user3));

            // Creating scores for users
            Score score1 = new Score(user1, 85); 
            Score score2 = new Score(user1, 90);
            Score score3 = new Score(user2, 75);
            Score score4 = new Score(user2, 88);
            Score score5 = new Score(user3, 92);

            // Saving scores to the database
            scoreRepository.saveAll(Arrays.asList(score1, score2, score3, score4, score5));

            
            
           
        };
    }
}
