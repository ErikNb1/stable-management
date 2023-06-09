package de.kisters.stablemanagement.util.exception.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadArgumentsException extends RuntimeException {

  public BadArgumentsException(String message) {
    super(message);
  }
}
