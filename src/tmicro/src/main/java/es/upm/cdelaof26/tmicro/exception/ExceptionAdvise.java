package es.upm.cdelaof26.tmicro.exception;

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
    @ExceptionHandler(CurrencyExchangeUnavailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage currencyExchangeUnavailableHandler(CurrencyExchangeUnavailableException ex) {
        return new ErrorMessage(ex.getMessage());
    }
    
    @ExceptionHandler(CurrencyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage currencyNotFoundHandler(CurrencyNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
