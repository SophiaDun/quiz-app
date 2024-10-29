package app.demo.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import app.demo.domain.QuizUser;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final QuizUser quizUser;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(QuizUser quizUser, Collection<? extends GrantedAuthority> authorities) {
        this.quizUser = quizUser;
        this.authorities = authorities;
    }

    public QuizUser getQuizUser() {
        return this.quizUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return quizUser.getPassword();
    }

    @Override
    public String getUsername() {
        return quizUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
