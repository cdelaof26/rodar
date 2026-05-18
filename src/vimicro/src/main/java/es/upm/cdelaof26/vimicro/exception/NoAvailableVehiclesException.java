package es.upm.cdelaof26.vimicro.exception;

/**
 * RuntimeException thrown when a there are no vehicles in a date range
 * @author cristopher
 */
public class NoAvailableVehiclesException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "No hay vehículos del tipo {@code inventoryId} en el rango de fechas {@code startDate} a {@code endDate}"
     * 
     * @param inventoryId the inventory identifier
     * @param startDate the initial date
     * @param endDate the final date
     */
    public NoAvailableVehiclesException(int inventoryId, String startDate, String endDate) {
        super(String.format("No hay vehículos disponibles del tipo %d en el rango de fechas %s a %s", inventoryId, startDate, endDate));
    }
}
