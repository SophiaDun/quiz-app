package app.demo.web;

import app.demo.domain.Question;
import app.demo.domain.QuizUser;
import app.demo.domain.Score;
import app.demo.service.QuizService;
import app.demo.domain.ScoreRepository; 

import java.util.List; 
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

    @Autowired
    public QuizController(QuizService quizService, ScoreRepository scoreRepository) {
        this.quizService = quizService;
        this.scoreRepository = scoreRepository; 
    }

    @GetMapping
    public String getQuizPage(@RequestParam(required = false, defaultValue = "easy") String difficulty, Model model) {
        model.addAttribute("difficulty", difficulty); // Pass difficulty to the quiz page
        quizService.resetQuiz(); // Reset the quiz when starting a new one
        return "quiz"; 
    }

    @GetMapping("/next")
    public ResponseEntity<Question> getNextQuestion(@RequestParam String difficulty) {
        Question question = quizService.getNextQuestion(difficulty);
        return ResponseEntity.ok(question);
    }
    
    @PostMapping("/answer")
    public ResponseEntity<Boolean> submitAnswer(@AuthenticationPrincipal QuizUser user,
                                                @RequestParam String selectedAnswer,
                                                @RequestParam String correctAnswer) {
        boolean isCorrect = selectedAnswer.equals(correctAnswer); // Validate the answer
        if (isCorrect) {
            quizService.saveScore(user, 1); // Save score only if the answer is correct
        }
        return ResponseEntity.ok(isCorrect); // Return the correctness of the answer
    }
    

    @PostMapping("/submit")
    public ResponseEntity<Integer> submitQuiz(@AuthenticationPrincipal QuizUser user) {
        int score = quizService.getQuestionsAnswered(); // Get the score based on answered questions
        quizService.saveScore(user, score); // Save the score for the user
        quizService.resetQuiz(); // Reset the quiz after submission
        return ResponseEntity.ok(score); // Return the score
    }

    @GetMapping("/home")
    public String getHomePage(Model model, @AuthenticationPrincipal QuizUser user) {
        // Fetch the total score for the authenticated user
        int totalScore = scoreRepository.getTotalScore(user); // Ensure this method exists in your repository
        List<Score> topScores = scoreRepository.findTop10ByOrderByPointsDesc(); // Fetch top scores
    
        // Add attributes to the model for Thymeleaf
        model.addAttribute("user", user);
        model.addAttribute("totalScore", totalScore);
        model.addAttribute("topScores", topScores);
        
        return "home"; 
    }
    
}
