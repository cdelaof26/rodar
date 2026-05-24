package es.upm.cdelaof26.pmicro.exception;

/**
 * RuntimeException thrown when a status is not found by its id
 * @author cristopher
 */
public class InvalidStatusException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El estado '{@code id}' no existe"
     * 
     * @param id the provider id
     */
    public InvalidStatusException(String id) {
        super(String.format("El estado '%s' no existe", id));
    }
}
