
package app.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByQuizUser(QuizUser quizUser);
    
    @Query("SELECT SUM(s.points) FROM Score s WHERE s.quizUser = :quizUser")
    Integer getTotalScore(@Param("quizUser") QuizUser quizUser);
    
    List<Score> findTop10ByOrderByPointsDesc();
}


