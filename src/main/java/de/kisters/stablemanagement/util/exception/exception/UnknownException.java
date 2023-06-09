package de.kisters.stablemanagement.util.exception.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnknownException extends RuntimeException {

  public UnknownException(String message) {
    super(message);
  }
}
