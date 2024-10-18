package org.mailboxclubindonesia.backoffice.config;

import org.mailboxclubindonesia.backoffice.filter.AuthenticationFilter;
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
  FilterRegistrationBean<AuthenticationFilter> secondFilter() {
    FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new AuthenticationFilter(authenticationService));
    registrationBean.addUrlPatterns("/api/*");
    registrationBean.setOrder(0);
    return registrationBean;
  }
}
