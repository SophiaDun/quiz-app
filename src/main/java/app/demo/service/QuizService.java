package app.demo.service;

import app.demo.domain.ApiResponse; 
import app.demo.domain.Question;
import app.demo.domain.QuizUser;
import app.demo.domain.Score;
import app.demo.domain.ScoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuizService {
    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private List<Question> questions = new ArrayList<>();
    private final Random random = new Random();
    private final ScoreRepository scoreRepository;

    private int questionsAnswered = 0; // Counter for answered questions
    private static final int MAX_QUESTIONS = 10; // Maximum number of questions

    public QuizService(RestTemplate restTemplate, ObjectMapper objectMapper, ScoreRepository scoreRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.scoreRepository = scoreRepository;
    }

    public Question getNextQuestion(String difficulty) {
        if (questionsAnswered >= MAX_QUESTIONS) {
            logger.info("Max questions reached. Returning null to indicate end of quiz.");
            return null; // Return null if max questions have been answered
        }
        
        logger.info("Fetching next question for difficulty: {}", difficulty);
        if (questions.isEmpty()) {
            fetchQuestions(difficulty);
        }
        Question question = fetchRandomQuestion();
        if (question == null) {
            logger.error("No questions available for the specified difficulty.");
            throw new RuntimeException("No questions available for the specified difficulty.");
        }
        logger.info("Returning question: {}", question.getQuestion());
        questionsAnswered++; // Increment the counter after fetching a question
        return question;
    }

    private void fetchQuestions(String difficulty) {
        String url = UriComponentsBuilder.fromHttpUrl("https://opentdb.com/api.php")
        .queryParam("amount", 100)
        .queryParam("difficulty", difficulty)
        .queryParam("type", "boolean")
        .toUriString();
        
        logger.info("Fetching questions from API: {}", url);
        try {
            String response = restTemplate.getForObject(url, String.class);
            logger.debug("API Response: {}", response); 
            ApiResponse<Question> apiResponse = objectMapper.readValue(
                response,
                objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, Question.class)
            );
    
            if (apiResponse != null && apiResponse.getResults() != null) {
                questions.addAll(apiResponse.getResults());
                logger.info("Fetched {} questions", apiResponse.getResults().size());
            } else {
                logger.warn("No questions found for difficulty: {}", difficulty);
            }
        } catch (Exception e) {
            logger.error("Error fetching questions from API", e);
            questions.clear(); 
        }
    }

    private Question fetchRandomQuestion() {
        if (questions.isEmpty()) {
            logger.warn("No more questions available to fetch.");
            return null; // No more questions available
        }
        return questions.remove(random.nextInt(questions.size()));
    }

    public void saveScore(QuizUser quizUser, int points) {
        // Retrieve the current score of the user
        Integer currentScore = scoreRepository.getTotalScore(quizUser); // Fetch current score

        // If no score exists, initialize it to zero
        if (currentScore == null) {
            currentScore = 0;
        }

        // Create a new score entry
        Score score = new Score();
        score.setQuizUser(quizUser);
        score.setPoints(points);

        // Save the score for this game session
        scoreRepository.save(score);

        // Update the total score for the user
        int newTotalScore = currentScore + points;
  
        quizUser.setTotalScore(newTotalScore); // Update user's total score
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public void resetQuiz() {
        questions.clear();
        questionsAnswered = 0; 
    }
}
