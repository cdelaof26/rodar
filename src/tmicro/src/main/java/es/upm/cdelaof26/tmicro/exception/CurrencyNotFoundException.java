package es.upm.cdelaof26.tmicro.exception;

/**
 * RuntimeException thrown when a currency is not found by its id
 * @author cristopher
 */
public class CurrencyNotFoundException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "La divisa '{@code id}' no existe"
     * 
     * @param id the currency id
     */
    public CurrencyNotFoundException(String id) {
        super(String.format("La divisa '%s' no existe", id));
    }
}
