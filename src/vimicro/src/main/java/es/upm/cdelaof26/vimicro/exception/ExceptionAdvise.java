package es.upm.cdelaof26.vimicro.exception;

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
    
    @ExceptionHandler(VehicleAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage vehicleFoundExceptionHandler(VehicleAlreadyExistException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(InventoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage providerNotFoundHandler(InventoryNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(UnsupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage unsupportedOperationHandler(UnsupportedException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(NoAvailableVehiclesException.class)
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    public ErrorMessage unsupportedOperationHandler(NoAvailableVehiclesException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
