package es.upm.cdelaof26.vsmicro.exception;

/**
 * RuntimeException thrown when a date doesn't follow the format yyyy-mm-dd
 * @author cristopher
 */
public class InvalidDateException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El valor '{@code date}' no es una fecha válida"
     * 
     * @param date the date
     */
    public InvalidDateException(String date) {
        super(String.format("El valor '%s' no es una fecha válida", date));
    }
}
