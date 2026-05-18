package es.upm.cdelaof26.vimicro.exception;

/**
 * RuntimeException thrown when a field is missing
 * @author cristopher
 */
public class MissingFieldException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El campo {@code field} es necesario"
     * 
     * @param field the field
     */
    public MissingFieldException(String field) {
        super(String.format("El campo %s es necesario", field));
    }
}
