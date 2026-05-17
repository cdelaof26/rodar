package es.upm.cdelaof26.pmmicro.exception;

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
    @ExceptionHandler(FieldAlreadyTaken.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage alreadyInUseHandler(FieldAlreadyTaken ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(ProviderNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage providerNotFoundHandler(ProviderNotFound ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
