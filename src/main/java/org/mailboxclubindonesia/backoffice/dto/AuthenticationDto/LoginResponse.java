package org.mailboxclubindonesia.backoffice.dto.AuthenticationDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

  @NotEmpty
  private String token;

  public LoginResponse(String token) {
    this.token = token;
  }
}
