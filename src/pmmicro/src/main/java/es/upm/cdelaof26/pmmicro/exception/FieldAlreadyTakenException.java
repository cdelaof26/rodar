package es.upm.cdelaof26.pmmicro.exception;

/**
 * RuntimeException thrown when an unique field is taken
 * @author cristopher
 */
public class FieldAlreadyTakenException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El {@code field} '{@code takenValue}' ya está registrado"
     * 
     * @param field the field
     * @param takenValue the value already in use
     */
    public FieldAlreadyTakenException(String field, String takenValue) {
        super(String.format("El %s '%s' ya está registrado", field, takenValue));
    }
}
