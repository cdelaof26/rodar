package es.upm.cdelaof26.remicro.exception;

/**
 * RuntimeException thrown when there are no vehicles available in the range 
 * of dates
 * @author cristopher
 */
public class NoInventoryException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "No hay inventario suficiente de vehículos {@code vehicleId} en las fechas de {@code startDate} a {@code endDate}"
     * @param vehicleId the unavailable vehicle
     * @param startDate the start date
     * @param endDate the end date
     */
    public NoInventoryException(int vehicleId, String startDate, String endDate) {
        super(String.format("No hay inventario suficiente de vehículos %d en las fechas de %s a %s", vehicleId, startDate, endDate));
    }
}
