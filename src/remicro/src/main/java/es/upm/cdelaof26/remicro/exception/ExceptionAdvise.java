package es.upm.cdelaof26.remicro.exception;

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
    public ErrorMessage invalidDateHandler(InvalidDateException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(LendTimeTooLongException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage timeTooLongHandler(LendTimeTooLongException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(NoInventoryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage noInventoryHandler(NoInventoryException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(ReservationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage reservationNotFoundHandler(ReservationNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
