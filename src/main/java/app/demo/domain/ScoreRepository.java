package app.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    
    // Find scores by user
    List<Score> findByUser(QuizUser user);
    
    // Get top 10 scores ordered by points descending
    List<Score> findTop10ByOrderByPointsDesc();
    
    // calculate the total score for a specific user
    @Query("SELECT COALESCE(SUM(s.points), 0) FROM Score s WHERE s.user = :user")
    Integer getTotalScore(@Param("user") QuizUser user);
}
