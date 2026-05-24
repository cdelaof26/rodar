package es.upm.cdelaof26.vimicro.service;

import es.upm.cdelaof26.vimicro.exception.InvalidDateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date validation
 * @author cristopher
 */
public class DateValidationUtil {
    private static final Logger l = LoggerFactory.getLogger(DateValidationUtil.class);
    
    public static void validateDate(String d, String name) {
        if (!d.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            l.error(String.format("Invalid value '%s' for %s", d, name));
            throw new InvalidDateException(d);
        }
    }
}
