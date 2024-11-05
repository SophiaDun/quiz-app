package app.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

 @Autowired
 CustomUserDetailsService customUserDetailsService;

 @Bean
 public static PasswordEncoder passwordEncoder() {
  return new BCryptPasswordEncoder();
 }
@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/css/**", "/register", "/login", "/h2-console/**").permitAll()
		.requestMatchers("/quiz/**").authenticated() 
			.anyRequest().authenticated()
		)
		.headers(headers -> headers
				.frameOptions(frameoptions -> 
				frameoptions.disable() 		
			    )
		)
		.formLogin(formlogin -> formlogin
				.loginPage("/login")
				.defaultSuccessUrl("/home", true)
				.permitAll()
		)
		.logout(logout -> logout
        .logoutUrl("/logout") 
        .logoutSuccessUrl("/login?logout") 
        .invalidateHttpSession(true) 
        .clearAuthentication(true) 
				.permitAll()
		);
				
		return http.build();
	}

 @Autowired
 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());

 }
}
