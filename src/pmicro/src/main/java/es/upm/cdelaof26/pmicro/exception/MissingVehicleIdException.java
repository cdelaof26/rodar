package es.upm.cdelaof26.pmicro.exception;

/**
 * RuntimeException thrown when a vehicle id is not present
 * under certain conditions
 * @author cristopher
 */
public class MissingVehicleIdException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "Se requiere del identificador del coche"
     */
    public MissingVehicleIdException() {
        super("Se requiere del identificador del coche");
    }
}
