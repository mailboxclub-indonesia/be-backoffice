package org.mailboxclubindonesia.backoffice.config;

import org.mailboxclubindonesia.backoffice.filter.AuthenticationFilter;
import org.mailboxclubindonesia.backoffice.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final AuthenticationService authenticationService;

  public SecurityConfig(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.disable())
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(new AuthenticationFilter(authenticationService),
                UsernamePasswordAuthenticationFilter.class));
    return http.build();
  }
}
