package es.upm.cdelaof26.pmmicro.exception;

/**
 * RuntimeException thrown when a provider is not found by its id
 * @author cristopher
 */
public class ProviderNotFoundException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El proveedor con el identificador '{@code id}' no existe"
     * 
     * @param id the provider id
     */
    public ProviderNotFoundException(int id) {
        super(String.format("El proveedor con el identificador '%d' no existe", id));
    }
}
