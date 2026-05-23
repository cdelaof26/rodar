package es.upm.cdelaof26.pmicro.exception;

/**
 * RuntimeException thrown when a payment method is not found or is not given
 * under certain conditions
 * @author cristopher
 */
public class PaymentMethodUnsupportedException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El método de pago '{@code name}' no existe o no esta soportado"
     * 
     * @param name the payment method
     */
    public PaymentMethodUnsupportedException(String name) {
        super(String.format("El método de pago '%s' no existe o no esta soportado", name));
    }
    
    /**
     * Creates a new exception with a message that reads,
     * 
     * "Se require de un método de pago"
     */
    public PaymentMethodUnsupportedException() {
        super("Se require de un método de pago");
    }
}
