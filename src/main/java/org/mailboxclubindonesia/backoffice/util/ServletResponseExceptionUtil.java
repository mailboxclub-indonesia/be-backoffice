package org.mailboxclubindonesia.backoffice.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServletResponseExceptionUtil {

  public static void setErrorResponse(HttpServletRequest request, HttpServletResponse response, HttpStatus status,
      String message) throws IOException {
    try {
      String timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
      String errorResponse = String.format(
          "{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
          timestamp, status.value(), status.getReasonPhrase(), message, request.getRequestURI());

      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().write(errorResponse);
    } catch (IOException exception) {
      exception.printStackTrace();
      throw exception;
    }
  }
}
