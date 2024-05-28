package com.hardeymorlah.Logistics.exception;//package com.hardeymorlah.Logistics.exception;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.hibernate.NonUniqueResultException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public String handleSQLIntegrityViolation(SQLIntegrityConstraintViolationException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidDefinitionException.class)
    public String handleInvalidDefinition(InvalidDefinitionException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegal(IllegalArgumentException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentInvalidException(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));

       /* BindingResult bindingResult = exception.getBindingResult();
       List<FieldError> errors = bindingResult.getFieldErrors();
        for( FieldError error: errors ){
            errorMap.put(error.getField(), error.getDefaultMessage());
       }*/
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InstantiationException.class)
    public String handleInstantiationException(InstantiationException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleMethodTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElement(NoSuchElementException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RegistrationException.class)
    public String handleRegistrationException(RegistrationException exception) {
        return exception.getMessage();
    }
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(BadCredentialsException.class)
        public String handleRBadCredentials (BadCredentialsException exception){
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Email or Password");
        return exception.getMessage();
        }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PackageNotFoundException.class)
    public String handlePackageException(PackageNotFoundException exception) {
        return exception.getMessage();
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NonUniqueResultException.class)
    public String handlePackageException(NonUniqueResultException exception) {
        return exception.getMessage();
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DuplicateKeyException.class)
    public String handleConstraintException(DuplicateKeyException exception) {
        return STR."\{exception.getMessage()}User already exist";
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleMethodException(ConstraintViolationException exception) {
        return STR."\{exception.getMessage()}User already exist";
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ValidationException.class)
    public String handleConstraintException(ValidationException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InsufficientStockException.class)
    public String handleConstraintException(InsufficientStockException exception) {
        return exception.getMessage();
    }
}






