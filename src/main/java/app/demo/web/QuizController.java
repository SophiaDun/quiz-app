package app.demo.web;

import app.demo.domain.Question;
import app.demo.domain.QuizUser;
import app.demo.domain.Score;
import app.demo.service.QuizService;
import app.demo.domain.ScoreRepository;
import app.demo.config.CustomUserDetails;
import app.demo.domain.AnswerDTO; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;
    private final ScoreRepository scoreRepository;

   
    public QuizController(QuizService quizService, ScoreRepository scoreRepository) {
        this.quizService = quizService;
        this.scoreRepository = scoreRepository;
    }

    @GetMapping
    public String getQuizPage(@RequestParam(required = false, defaultValue = "easy") String difficulty, Model model) {
        model.addAttribute("difficulty", difficulty);
        quizService.resetQuiz(); 
        return "quiz"; 
    }
    

    @GetMapping("/next")
    public ResponseEntity<Question> getNextQuestion(@RequestParam String difficulty) {
        Question question = quizService.getNextQuestion(difficulty);
        return ResponseEntity.ok(question);
    }

    @PostMapping("/answer")
    public ResponseEntity<Boolean> submitAnswer(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @RequestBody AnswerDTO answer) {
        System.out.println("Received answer: " + answer);
        QuizUser user = userDetails.getQuizUser(); // Access the QuizUser from CustomUserDetails
        boolean isCorrect = answer.getSelectedAnswer().equals(answer.getCorrectAnswer()); // Validate the answer
        if (isCorrect) {
            quizService.saveScore(user, 1); // Save score only if the answer is correct
        }
        return ResponseEntity.ok(isCorrect); // Return the correctness of the answer
    }
    

    @PostMapping("/submit")
    public ResponseEntity<Integer> submitQuiz(@AuthenticationPrincipal CustomUserDetails userDetails) {
        QuizUser user = userDetails.getQuizUser(); // Access the QuizUser from CustomUserDetails
        int score = quizService.getQuestionsAnswered(); // Get the score based on answered questions
        quizService.saveScore(user, score); // Save the score for the user
        quizService.resetQuiz(); // Reset the quiz after submission
        return ResponseEntity.ok(score); // Return the score
    }
}
