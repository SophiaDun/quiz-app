package app.demo.config;
import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.demo.domain.QuizUser;
import app.demo.domain.QuizUserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final QuizUserRepository quizUserRepository;

    @Autowired
    public CustomUserDetailsService(QuizUserRepository quizUserRepository) {
        this.quizUserRepository = quizUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QuizUser quizUser = quizUserRepository.findByUsername(username);
        if (quizUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        return new CustomUserDetails(quizUser, Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}



