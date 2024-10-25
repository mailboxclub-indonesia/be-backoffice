package org.mailboxclubindonesia.backoffice.dto.AuthenticationDto;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.mailboxclubindonesia.backoffice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSecurityDetails implements UserDetails {
  private final User user;

  public UserSecurityDetails(User user) {
    this.user = user;
  }

  public UUID getId() {
    return user.getId();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public String getPassword() {
    return user.getHash();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
