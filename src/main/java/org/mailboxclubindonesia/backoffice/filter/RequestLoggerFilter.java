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

  private void logRequest(ContentCachingRequestWrapper wrappedRequest) {
    String requestBody = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
    logger.info("RequestMethod: " + wrappedRequest.getMethod() + " " +
        wrappedRequest.getRequestURI());
    logger.info("RequestBody: " + requestBody);
  }

  private void logResponse(ContentCachingResponseWrapper wrappedResponse) {
    String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);
    logger.info("ResponseStatus: " + wrappedResponse.getStatus());
    logger.info("ResponseBody: " + responseBody);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    try {
      ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
      ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

      chain.doFilter(wrappedRequest, wrappedResponse);

      logRequest(wrappedRequest);
      logResponse(wrappedResponse);

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
