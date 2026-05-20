package es.upm.cdelaof26.tmicro.repository;

import java.util.Currency;
import java.util.Optional;

/**
 * Taxes repository
 * @author cristopher
 */
public class TaxesRepository {
    public Optional<Currency> findCurrencyByName(String name) {
        try {
            return Optional.of(Currency.getInstance(name));
        } catch (IllegalArgumentException ex) {
            return Optional.ofNullable(null);
        }
    }
}
