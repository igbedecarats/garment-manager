package com.gm.producer.components.shared;

import com.gm.producer.components.product.usecase.MessagingException;
import io.micrometer.core.instrument.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class handles exceptions raised in any part of the external
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

  /**
   * Catch and log Spring MVC specific exceptions
   */
  @NotNull
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
          final Exception ex, final Object body, final HttpHeaders headers, final HttpStatus status,
          final WebRequest request) {
    logger.error("Spring MVC exception occurred", ex);
    return super.handleExceptionInternal(ex, body, headers, status, request);
  }

  @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class, MessagingException.class})
  @ResponseBody
  public ResponseEntity<?> handleBadRequests(final Exception ex) {
    return toResponseEntity(ex, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({Exception.class})
  @ResponseBody
  public ResponseEntity<?> handleInternalServerErrors(final Exception ex) {
    return toResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<HttpApiError> toResponseEntity(
      final Throwable throwable,
      final HttpStatus httpStatus) {
    log(throwable);
    HttpApiError error = HttpApiError.create(httpStatus, throwable.getMessage());
    return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
  }

  private void log(final Throwable throwable) {
    String errorMessage = throwable.getMessage();
    logger.error(errorMessage, throwable);
  }
}
