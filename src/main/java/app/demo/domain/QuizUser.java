package app.demo.domain;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "QUIZUSER") 
public class QuizUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
    
    @OneToMany(mappedBy = "quizUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Score> scores = new ArrayList<>(); 

    @Column(name = "total_score")
    private Integer totalScore; 

    public QuizUser() {}

    public QuizUser(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public void recalculateTotalScore() {
        this.totalScore = scores.stream().mapToInt(Score::getPoints).sum();
    }
    

    public void addScore(Score score) {
        score.setQuizUser(this);
        this.scores.add(score);
        recalculateTotalScore(); // Update total score when a new score is added
    }
    

  
    public void removeScore(Score score) {
        score.setQuizUser(null); 
        this.scores.remove(score);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}
