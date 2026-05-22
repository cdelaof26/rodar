package es.upm.cdelaof26.vsmicro.exception;

/**
 * RuntimeException thrown when more than two prices are given
 * @author cristopher
 */
public class InvalidPricingRangeException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El rango de precios no es válido"
     */
    public InvalidPricingRangeException() {
        super("El rango de precios no es válido");
    }
}
