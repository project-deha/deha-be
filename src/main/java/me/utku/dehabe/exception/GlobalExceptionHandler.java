package me.utku.dehabe.exception;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.utku.dehabe.generic.GenericResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return ResponseEntity.status(status).body(GenericResponse.error(status.value(), ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        return ResponseEntity.status(status).body(GenericResponse.error(status.value(), ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GenericResponse<Object>> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {
        log.info("DataIntegrityViolationException: {}.", e.getMessage());
        return GenericResponse.error(HttpStatus.CONFLICT.value(), "Data integrity violation.").toResponseEntity();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<GenericResponse<Object>> usernameNotFoundExceptionHandler(UsernameNotFoundException e) {
        log.info("UsernameNotFoundException: {}.", e.getMessage());
        return GenericResponse.error(HttpStatus.UNAUTHORIZED.value(), "No user found with this username.").toResponseEntity();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GenericResponse<Object>> badCredentialsExceptionHandler(BadCredentialsException e) {
        log.info("BadCredentialsException: {}.", e.getMessage());
        return GenericResponse.error(HttpStatus.UNAUTHORIZED.value(), "No user found with this ssn or password.").toResponseEntity();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GenericResponse<Object>> accessDeniedExceptionHandler(AccessDeniedException e) {
        log.info("AccessDeniedException: {}.", e.getMessage());
        return GenericResponse.error(HttpStatus.FORBIDDEN.value(), "You don't have rights to access this resource.").toResponseEntity();
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<GenericResponse<Object>> insufficientAuthenticationExceptionHandler(InsufficientAuthenticationException e) {
        log.info("InsufficientAuthenticationException: {}.", e.getMessage());
        return GenericResponse.error(HttpStatus.UNAUTHORIZED.value(), "You need to be authenticated and have enough rights to access this resource.").toResponseEntity();
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<GenericResponse<Object>> entityNotFoundExceptionHandler(EmailSendingException e) {
        log.info("EmailSendingException: {}.", e.getMessage());
        return GenericResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()).toResponseEntity();
    }

    @ExceptionHandler(EmailVerificationException.class)
    public ResponseEntity<GenericResponse<Object>> operationDeniedExceptionHandler(EmailVerificationException e) {
        log.info("EmailVerificationException: {}.", e.getMessage());
        return GenericResponse.error(HttpStatus.FORBIDDEN.value(), e.getMessage()).toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Object>> unhandledExceptionHandler(Exception e) {
        log.info("Exception (UNHANDLED): {}.", e.getMessage());
        return GenericResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong. " + "(" + e.getMessage() + ")").toResponseEntity();
    }
}

