package org.mailboxclubindonesia.backoffice.dto.AuthenticationDto;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.mailboxclubindonesia.backoffice.model.User;

@Getter
@Setter
public class UserRegisterResponse {
  @NotNull
  public UUID id;

  @NotEmpty
  public String email;

  public UserRegisterResponse(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
  }
}
