package app.demo.domain;
import org.springframework.data.repository.CrudRepository;





public interface QuizUserRepository extends CrudRepository<QuizUser, Long> {
    QuizUser findByUsername(String username);
    boolean existsByUsername(String username); 
}


