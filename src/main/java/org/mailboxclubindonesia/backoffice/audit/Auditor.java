package org.mailboxclubindonesia.backoffice.audit;

import org.mailboxclubindonesia.backoffice.dto.AuthenticationDto.UserSecurityDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

/**
 * Auditor class responsible for providing the current authenticated user's ID
 * for auditing purposes. Implements the {@link AuditorAware} interface to
 * integrate with Spring Data JPA's auditing functionality.
 */
@Component
public class Auditor implements AuditorAware<UUID> {

  /**
   * Retrieves the ID of the current authenticated user from the security context.
   *
   * @return an {@link Optional} containing the String of the authenticated user,
   *         or
   *         an empty Optional if the principal is not an instance of
   *         {@link UserSecurityDetails}.
   */
  @Override
  public Optional<UUID> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication instanceof AnonymousAuthenticationToken) {
      return Optional.empty();
    }

    Object principal = authentication.getPrincipal();
    UserSecurityDetails currentAuthenticatdUser = (UserSecurityDetails) principal;
    UUID userId = currentAuthenticatdUser.getId();

    return Optional.of(userId);
  }
}
