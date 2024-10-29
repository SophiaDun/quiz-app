package app.demo.domain;



import lombok.Data;
import java.util.List;

@Data
public class QuestionResponse {
    private int response_code;
    private List<Question> results;
}

