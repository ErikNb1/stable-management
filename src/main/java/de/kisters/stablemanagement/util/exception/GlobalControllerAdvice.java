package de.kisters.stablemanagement.util.exception;

import de.kisters.stablemanagement.util.exception.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ErrorDto> handleAuthenticationException(AuthenticationException ex) {
    logError(ex);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(ex.getMessage()));
  }
  private void logError(Throwable ex) {
    log.error("Exception handled: ", ex);
  }

  @ExceptionHandler(NoEntityFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorDto> handleNoEntityFoundException(NoEntityFoundException ex) {
    logError(ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ex.getMessage()));
  }
  @ExceptionHandler(NoSpaceInBuildingException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorDto> handleNoSpaceInBuildingException(NoSpaceInBuildingException ex) {
    logError(ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ex.getMessage()));
  }
  @ExceptionHandler(UserNotPermittedException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorDto> handleUserNotPermittedException(UserNotPermittedException ex) {
    logError(ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ex.getMessage()));
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    logError(ex);
    log.error("Validation exception. Return status 400.");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Invalid data", mapBindingResult(ex)));
  }

  private List<String> mapBindingResult(MethodArgumentNotValidException ex) {
    return ex.getBindingResult().getAllErrors().stream().map(objectError -> {

      if (objectError instanceof FieldError fieldError) {
        return fieldError.getObjectName() + "." + fieldError.getField() + " -> " + fieldError.getRejectedValue() + ": " + fieldError.getDefaultMessage();
      }
      return objectError.getObjectName() + ": " + objectError.getDefaultMessage();

    }).toList();
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorDto> handleBadArgumentsException(Exception ex) {
    logError(ex);
    log.error("Unknown Exception. Return generic message and status 500.");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto("Invalid request. Try again."));
  }

  @ExceptionHandler(UnsupportedOperationException.class)
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  public ResponseEntity<ErrorDto> handleUnsupportedOperationException(Exception ex) {
    logError(ex);
    log.error("Method not implemented, please wait for updates. Return status 501");
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ErrorDto("Unimplemented method. Don't try again."));
  }
}
