package es.upm.cdelaof26.pmicro.exception;

/**
 * RuntimeException thrown when a payment is not found by its id
 * @author cristopher
 */
public class PaymentNotFoundException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El registro de pago con el identificador '{@code id}' no existe"
     * 
     * @param id the provider id
     */
    public PaymentNotFoundException(int id) {
        super(String.format("El registro de pago con el identificador '%d' no existe", id));
    }
}
