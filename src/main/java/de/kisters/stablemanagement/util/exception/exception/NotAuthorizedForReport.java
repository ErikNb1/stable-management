package de.kisters.stablemanagement.util.exception.exception;

public class NotAuthorizedForReport extends RuntimeException {
  public NotAuthorizedForReport(String message) {
    super(message);
  }
}
