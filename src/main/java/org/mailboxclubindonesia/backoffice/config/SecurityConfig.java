package org.mailboxclubindonesia.backoffice.config;

import org.mailboxclubindonesia.backoffice.filter.AuthenticationFilter;
import org.mailboxclubindonesia.backoffice.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  public AuthenticationService authenticationService;

  public SecurityConfig(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.disable())
        .addFilterAfter(new AuthenticationFilter(authenticationService),
            LogoutFilter.class)
        .authorizeHttpRequests(authorize -> authorize
            .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
            .requestMatchers("api/auth/**").permitAll()
            .anyRequest()
            .authenticated());
    return http.build();
  }

}
