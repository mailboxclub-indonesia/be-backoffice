package org.mailboxclubindonesia.backoffice.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.mailboxclubindonesia.backoffice.config.JwtConfig;
import org.mailboxclubindonesia.backoffice.exception.PasswordMissmatchException;
import org.mailboxclubindonesia.backoffice.model.User;
import org.mailboxclubindonesia.backoffice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtConfig jwtConfig;

  public AuthenticationService(UserRepository userRepository, JwtConfig jwtConfig) {
    this.userRepository = userRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.jwtConfig = jwtConfig;
  }

  public String generateSalt() {
    byte[] salt = new byte[16];
    new SecureRandom().nextBytes(salt);
    return Base64.getEncoder().encodeToString(salt);
  }

  public User createUserPassword(String email, String password) {
    String randomSalt = generateSalt();
    String hash = passwordEncoder.encode(password + randomSalt);
    User user = new User(null, email, hash, randomSalt);

    User newUser = userRepository.save(user);
    return newUser;
  }

  public User authenticateUser(String email, String password) throws PasswordMissmatchException {
    User user = userRepository.findUserByEmail(email);

    if (user == null)
      throw new NoSuchElementException("User not found with the provided email");

    String hash = user.getHash();
    String salt = user.getSalt();
    boolean isPasswordMatch = passwordEncoder.matches(password + salt, hash);

    if (!isPasswordMatch)
      throw new PasswordMissmatchException("Password is not match");

    return user;
  }

  public String generateTokenFromUser(User user) {
    SecretKey key = new SecretKeySpec(jwtConfig.getSecret().getBytes(), "HmacSHA256");
    Date iat = new Date(System.currentTimeMillis());
    Date exp = new Date(System.currentTimeMillis() + jwtConfig.getExpiration());

    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", user.getId());
    claims.put("email", user.getEmail());

    return Jwts.builder()
        .claims()
        .add(claims)
        .issuedAt(iat)
        .expiration(exp)
        .and()
        .signWith(key)
        .compact();
  }

  public UUID getUserIdFromToken(String token) {
    SecretKey key = new SecretKeySpec(jwtConfig.getSecret().getBytes(), "HmacSHA256");

    Claims claims = (Claims) Jwts.parser()
        .verifyWith(key)
        .build()
        .parse(token)
        .getPayload();

    UUID userId = UUID.fromString((String) claims.get("userId"));
    return userId;
  }
}
