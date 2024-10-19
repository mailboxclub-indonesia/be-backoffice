package org.mailboxclubindonesia.backoffice.dto.UserDto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInstitutionRequest {
  @NotNull
  UUID userId;

  @NotNull
  UUID institutionId;
}
