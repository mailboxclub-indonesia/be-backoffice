package org.mailboxclubindonesia.backoffice.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.mailboxclubindonesia.backoffice.dto.AuthenticationDto.UserSecurityDetails;
import org.mailboxclubindonesia.backoffice.service.AuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends OncePerRequestFilter {

  public AuthenticationService authenticationService;
  private AntPathMatcher antPathMatcher;

  public AuthenticationFilter(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
    this.antPathMatcher = new AntPathMatcher();
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null) {
      throw new ServletException("Missing authorization header");
    }

    if (!authorizationHeader.startsWith("Bearer ")) {
      throw new ServletException("Missing token from authorization header");
    }

    String token = authorizationHeader.substring(7);
    UUID userId = authenticationService.getUserIdFromToken(token);
    UserSecurityDetails userSecurityDetails = authenticationService.getUserSecurityDetailsFromId(userId);

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userSecurityDetails.getUsername(),
        userSecurityDetails.getPassword(),
        userSecurityDetails.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    List<String> excludedPaths = Arrays.asList("/api/auth/**");

    // return excludedPaths.contains(path);
    return excludedPaths.stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
  }
}
