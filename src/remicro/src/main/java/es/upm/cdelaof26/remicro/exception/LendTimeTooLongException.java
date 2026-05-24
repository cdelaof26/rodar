package es.upm.cdelaof26.remicro.exception;

/**
 * RuntimeException thrown when a range dates is greater than 30 days
 * @author cristopher
 */
public class LendTimeTooLongException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El tiempo de alquiler no puede exceder 30 días (tiempo pedido: {@code days})"
     * 
     * @param days the days requested
     */
    public LendTimeTooLongException(long days) {
        super(String.format("El tiempo de alquiler no puede exceder 30 días (tiempo solicitado: %d)", days));
    }
}
