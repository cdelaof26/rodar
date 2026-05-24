package es.upm.cdelaof26.remicro.exception;

/**
 * RuntimeException thrown when a reservation is not found by its id
 * @author cristopher
 */
public class ReservationNotFoundException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "La reservación con el identificador '{@code id}' no existe"
     * 
     * @param id the reservation id
     */
    public ReservationNotFoundException(int id) {
        super(String.format("La reservación con el identificador '%d' no existe", id));
    }
}
