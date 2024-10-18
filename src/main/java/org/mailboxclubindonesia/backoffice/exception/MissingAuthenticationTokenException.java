
package org.mailboxclubindonesia.backoffice.exception;

public class MissingAuthenticationTokenException extends RuntimeException {

  public MissingAuthenticationTokenException(String message) {
    super(message);
  }

}
