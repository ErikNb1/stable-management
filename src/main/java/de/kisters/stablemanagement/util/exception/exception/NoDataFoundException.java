package de.kisters.stablemanagement.util.exception.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoDataFoundException extends RuntimeException {
  public NoDataFoundException(String message) {
    super(message);
  }
}
