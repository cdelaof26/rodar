package es.upm.cdelaof26.vsmicro.exception;

/**
 * RuntimeException thrown when an object is not found by its id
 * @author cristopher
 */
public class ObjectNotFoundException extends RuntimeException {
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El {@code obj} con el identificador '{@code id}' no existe"
     * 
     * @param obj the object name
     * @param id the vehicle id
     */
    public ObjectNotFoundException(String obj, int id) {
        super(String.format("El %s con el identificador '%d' no existe", obj, id));
    }
    
    /**
     * Creates a new exception with a message that reads,
     * 
     * "El {@code obj} con el {@code field} '{@code id}' no existe"
     * 
     * @param obj the object name
     * @param field the field used to perform the query
     * @param data the value
     */
    public ObjectNotFoundException(String obj, String field, String data) {
        super(String.format("El %s con el %s '%s' no existe", obj, field, data));
    }
}
