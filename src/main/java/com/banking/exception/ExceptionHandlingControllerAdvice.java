package com.banking.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Global Exception Handler to return custom message and status code */
@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

  private final Logger logger = LoggerFactory.getLogger(ExceptionHandlingControllerAdvice.class);

  /**
   * Handle user input errors
   *
   * @param ex illegal argument exception
   * @return http entity with custom illegal argument exception message and error code 400
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public HttpEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
    logger.error(ex.getMessage(), ex);
    return new HttpEntity<Object>(ex.getMessage());
  }

  /**
   * Handle database errors
   *
   * @param ex data access exception
   * @return http entity with custom database exception message and error code 500
   */
  @ExceptionHandler(DataAccessException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public HttpEntity<Object> handleDataAccessException(DataAccessException ex) {
    logger.error(ex.getMessage(), ex);
    return new HttpEntity<Object>("Database error");
  }

  /**
   * Handle all unexcepted mvc errors
   *
   * @param ex unexcepted mvc exception
   * @return http entity with custom exception message and error code 500
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public HttpEntity<Object> handleException(Exception ex) {
    logger.error(ex.getMessage(), ex);
    return new HttpEntity<Object>("Something went wrong");
  }
}
