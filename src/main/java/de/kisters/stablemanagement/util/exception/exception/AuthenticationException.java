package de.kisters.stablemanagement.util.exception.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthenticationException extends RuntimeException {

  public AuthenticationException(String message) {
    super(message);
  }
}
