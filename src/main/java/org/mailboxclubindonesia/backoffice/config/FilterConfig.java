package org.mailboxclubindonesia.backoffice.config;

import org.mailboxclubindonesia.backoffice.filter.AuthenticationFilter;
import org.mailboxclubindonesia.backoffice.filter.RequestLoggerFilter;
import org.mailboxclubindonesia.backoffice.service.AuthenticationService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  private final AuthenticationService authenticationService;

  public FilterConfig(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Bean
  FilterRegistrationBean<RequestLoggerFilter> requestLogger() {
    FilterRegistrationBean<RequestLoggerFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new RequestLoggerFilter());
    registrationBean.addUrlPatterns("/api/*");
    return registrationBean;
  }

  @Bean
  FilterRegistrationBean<AuthenticationFilter> autheticationFilter() {
    FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new AuthenticationFilter(authenticationService));
    registrationBean.addUrlPatterns("/api/*");
    return registrationBean;
  }
}
