package es.upm.cdelaof26.vimicro.exception;

/**
 * RuntimeException thrown when an inventory is not found by its id
 * @author cristopher
 */
public class InventoryNotFoundException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El coche con el identificador '{@code id}' no existe"
     * 
     * @param id the inventory id
     */
    public InventoryNotFoundException(int id) {
        super(String.format("El coche con el identificador '%d' no existe", id));
    }
}
