package org.mailboxclubindonesia.backoffice.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestLoggerFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    try {
      ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
      ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

      chain.doFilter(wrappedRequest, wrappedResponse);

      String requestBody = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
      String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

      logger.info("RequestMethod: " + request.getMethod() + " " +
          request.getRequestURI());
      logger.info("RequestBody: " + requestBody);

      logger.info("ResponseStatus: " + response.getStatus());
      logger.info("ResponseBody: " + responseBody);

      wrappedResponse.copyBodyToResponse();

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
