package es.upm.cdelaof26.pmmicro.exception;

/**
 * RuntimeException thrown when a provider is not found by its id
 * @author cristopher
 */
public class ProviderNotFound extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El proveedor con el identificador '{@code id}' no existe"
     * 
     * @param id the provider id
     */
    public ProviderNotFound(int id) {
        super(String.format("El proveedor con el identificador '%d' no existe", id));
    }
}
