package es.upm.cdelaof26.pmicro.exception;

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
    @ExceptionHandler(FieldAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage alreadyInUseHandler(FieldAlreadyTakenException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage paymentNotFoundHandler(PaymentNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(PaymentMethodUnsupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage paymentMethodUnsupportedHandler(PaymentMethodUnsupportedException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
