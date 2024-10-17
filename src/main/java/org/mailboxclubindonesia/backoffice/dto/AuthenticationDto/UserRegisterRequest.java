package org.mailboxclubindonesia.backoffice.dto.AuthenticationDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
  @NotEmpty
  public String email;

  @NotEmpty
  public String password;

  @NotEmpty
  public String confirmPassword;
}
