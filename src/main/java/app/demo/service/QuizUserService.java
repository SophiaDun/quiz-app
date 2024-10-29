package app.demo.service;

import app.demo.domain.QuizUserRegistrationDto;

public interface QuizUserService {
    boolean existsByUsername(String username);
    void save(QuizUserRegistrationDto quizUserDto);
}
