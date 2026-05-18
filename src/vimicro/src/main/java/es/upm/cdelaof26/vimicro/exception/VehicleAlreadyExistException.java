package es.upm.cdelaof26.vimicro.exception;

/**
 * RuntimeException thrown when a license plate is already in db
 * @author cristopher
 */
public class VehicleAlreadyExistException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "La placa '{@code licensePlate}' ya esta registrada"
     * 
     * @param licensePlate the license plate
     */
    public VehicleAlreadyExistException(String licensePlate) {
        super(String.format("La placa '%s' ya esta registrada", licensePlate));
    }
}
