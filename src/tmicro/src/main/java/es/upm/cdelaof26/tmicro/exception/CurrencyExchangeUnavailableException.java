package es.upm.cdelaof26.tmicro.exception;

/**
 * RuntimeException thrown when a currency cannot be converted
 * @author cristopher
 */
public class CurrencyExchangeUnavailableException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "La divisa {@code currency} no se puede convertir"
     * 
     * @param currency the currency
     */
    public CurrencyExchangeUnavailableException(String currency) {
        super(String.format("La divisa %s no se puede convertir", currency));
    }
}
