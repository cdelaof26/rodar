package es.upm.cdelaof26.vimicro.exception;

/**
 * RuntimeException thrown when a combination of arguments is not implemented
 * @author cristopher
 */
public class UnsupportedException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "Operación no soportada"
     */
    public UnsupportedException() {
        super("Operación no soportada");
    }
}
