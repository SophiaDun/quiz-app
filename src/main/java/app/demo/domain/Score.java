package app.demo.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SCORE")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private QuizUser quizUser;

    @Column(name = "points", nullable = false)
    private int points;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public Score() {}

    public Score(QuizUser quizUser, int points) {
        this.points = points;
        this.timestamp = LocalDateTime.now();
        setQuizUser(quizUser); 
    }

    public Long getId() {
        return id;
    }

    public QuizUser getQuizUser() {
        return quizUser;
    }

    public void setQuizUser(QuizUser quizUser) {
        this.quizUser = quizUser;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Score [id=" + id + ", points=" + points + ", timestamp=" + timestamp + "]";
    }
}
