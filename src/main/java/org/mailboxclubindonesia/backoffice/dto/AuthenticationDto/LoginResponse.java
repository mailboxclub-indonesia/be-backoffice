package org.mailboxclubindonesia.backoffice.dto.AuthenticationDto;

import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

  @NotEmpty
  private UUID userId;

  @NotEmpty
  private String token;

  public LoginResponse(User user, String token) {
    this.userId = user.getId();
    this.token = token;
  }
}
