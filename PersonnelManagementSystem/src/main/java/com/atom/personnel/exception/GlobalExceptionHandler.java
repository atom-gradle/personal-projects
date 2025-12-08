package com.atom.personnel.exception;

import com.atom.personnel.dto.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AuthFailureException.class)
    public ResponseEntity<?> handleAuthFailureException(AuthFailureException ex) {
        logger.info(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        logger.info(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<?> handleEmployeeAlreadyExistsException(EmployeeAlreadyExistsException e) {
        logger.info("Catch a EmployeeAlreadyExistsException!");
        return ApiResponse.error(400, e.getMessage());
    }

    @ExceptionHandler(EmployeeNotExistsException.class)
    public ResponseEntity<?> handleEmployeeNotExistsException(EmployeeNotExistsException ex) {
        logger.info("Catch a EmployeeNotExistsException!");
        return ApiResponse.error(400, ex.getMessage());
    }

}
