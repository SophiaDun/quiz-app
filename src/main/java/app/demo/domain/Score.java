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
    private QuizUser user;

    @Column(name = "points", nullable = false)
    private int points;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

   
    public Score() {}

    public Score(QuizUser user, int points) {
        this.user = user;
        this.points = points;
        this.timestamp = LocalDateTime.now();
    }

  
    public Long getId() {
        return id;
    }

    public QuizUser getUser() {
        return user;
    }

    public void setUser(QuizUser user) {
        this.user = user;
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
}
