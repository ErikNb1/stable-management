package de.kisters.stablemanagement.util.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorDto {
  private final String message;
  private final List<String> errors;

  public ErrorDto(String message) {
    this(message, new ArrayList<>());
  }

  public ErrorDto(String message, List<String> errors) {
    this.message = message;
    this.errors = errors;
  }
}

