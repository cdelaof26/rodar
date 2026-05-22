package es.upm.cdelaof26.vsmicro.exception;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception advisor
 * @author cristopher
 */
@RestControllerAdvice
public class ExceptionAdvise {
    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage incorrectDateHandler(InvalidDateException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage providerNotFoundHandler(ObjectNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
