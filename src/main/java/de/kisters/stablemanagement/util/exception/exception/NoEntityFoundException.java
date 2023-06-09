package de.kisters.stablemanagement.util.exception.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoEntityFoundException extends RuntimeException {
  public NoEntityFoundException(String message) {
    super(message);
  }

  public NoEntityFoundException(Long id) {
    this("No Entity found for Id " + id);
  }
}
