package org.mailboxclubindonesia.backoffice.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.mailboxclubindonesia.backoffice.dto.AuthenticationDto.UserSecurityDetails;
import org.mailboxclubindonesia.backoffice.exception.MissingAuthenticationTokenException;
import org.mailboxclubindonesia.backoffice.service.AuthenticationService;
import org.mailboxclubindonesia.backoffice.util.ServletResponseExceptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationService authenticationService;

  public AuthenticationFilter(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    try {
      HttpServletRequest httpRequest = (HttpServletRequest) request;

      String authenticationToken = httpRequest.getHeader("X-Auth-Token");

      if (authenticationToken == null) {
        throw new MissingAuthenticationTokenException("Missing authentication token from request header");
      }

      UUID userId = authenticationService.getUserIdFromToken(authenticationToken);
      UserSecurityDetails userSecurityDetails = authenticationService.getUserSecurityDetailsFromId(userId);
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userSecurityDetails,
          null, userSecurityDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      request.setAttribute("userId", userId);
      chain.doFilter(request, response);
    } catch (MissingAuthenticationTokenException exception) {
      ServletResponseExceptionUtil.setErrorResponse(request, response, HttpStatus.UNAUTHORIZED, exception.getMessage());
    } catch (ServletException exception) {
      exception.printStackTrace();
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    List<String> excludedPath = Arrays.asList("/api/auth/login", "/api/auth/register");
    return excludedPath.contains(request.getRequestURI());
  }
}
