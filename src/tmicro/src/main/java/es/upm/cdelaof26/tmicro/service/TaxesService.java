package es.upm.cdelaof26.tmicro.service;

import es.upm.cdelaof26.tmicro.exception.CurrencyNotFoundException;
import es.upm.cdelaof26.tmicro.dto.PriceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import es.upm.cdelaof26.tmicro.repository.TaxesRepository;
import java.util.Currency;
import java.util.Optional;

/**
 * Taxes service
 * @author cristopher
 */
@Service
public class TaxesService {
    private final Logger l = LoggerFactory.getLogger(TaxesService.class);
    
    private final TaxesRepository taxesRepository;
    private final CurrencyExchangeService currencyExchangeService;

    public TaxesService(/* TaxesRepository repository */) {
        this.taxesRepository = new TaxesRepository();
        this.currencyExchangeService = new CurrencyExchangeService();
    }
    
    public float addTaxes(float amount) {
        return amount * 0.16f;
    }
    
    public PriceDto convertCurrency(String in, String out, float amount, boolean addTaxes) {
        Optional<Currency> optIn = taxesRepository.findCurrencyByName(in);
        Optional<Currency> optOut = taxesRepository.findCurrencyByName(out);
        
        if (optIn.isEmpty()) {
            l.error(String.format("Invalid input currency %s", in));
            throw new CurrencyNotFoundException(in);
        }
        if (optOut.isEmpty()) {
            l.error(String.format("Invalid output currency %s", out));
            throw new CurrencyNotFoundException(out);
        }
        
        Currency _in = optIn.get();
        Currency _out = optOut.get();
        
        if (_in.getCurrencyCode().equals(_out.getCurrencyCode()))
            return new PriceDto(_out.getDisplayName(), amount);
        
        float finalAmount = convertCurrency(_in, _out, amount);
        if (addTaxes)
            finalAmount += addTaxes(finalAmount);
        
        return new PriceDto(_out.getDisplayName(), finalAmount);
    }
    
    private float convertCurrency(Currency in, Currency out, float amount) {
        if (in.getCurrencyCode().equals("EUR")) 
            return currencyExchangeService.euroToCurrency(out) * amount;
        
        if (out.getCurrencyCode().equals("EUR"))
            return amount / currencyExchangeService.euroToCurrency(in);
        
        /**
         * 1 EUR -> 1.1620 USD
         * 1 EUR -> 184.89 JPY
         * 
         * 1 EUR      -> 1.1620 USD
         * 2.5817 EUR <- 3 USD
         * 2.5817 * 184.89 = 477.330513
         */
        
        float oneEuroAsInput = currencyExchangeService.euroToCurrency(in);
        float amountAsEuro = amount / oneEuroAsInput;
        
        return amountAsEuro * currencyExchangeService.euroToCurrency(out);
    }
}
