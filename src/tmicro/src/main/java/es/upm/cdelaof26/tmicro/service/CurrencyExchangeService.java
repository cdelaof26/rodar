package es.upm.cdelaof26.tmicro.service;

import es.upm.cdelaof26.tmicro.exception.CurrencyExchangeUnavailableException;
import java.util.Currency;

/**
 * Exchange service
 * @author cristopher
 */
public class CurrencyExchangeService {
    // TODO: Move to a text file or DB
    //       Probably not needed as this is supposed to be a dummy 
    //       microservice, though...
    
    // https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html
    // Values from May 20th, 2026
    //
    public float euroToCurrency(Currency c) {
        switch (c.getCurrencyCode()) {
            case "USD":
                return 1.1620f;
            case "JPY":
                return 184.89f;
            case "CZK":
                return 24.297f;
            case "DKK":
                return 7.4731f;
            case "GBP":
                return 0.86671f;
            case "HUF":
                return 360.65f;
            case "PLN":
                return 4.2435f;
            case "RON":
                return 5.2273f;
            case "SEK":
                return 10.9090f;
            case "CHF":
                return 0.9150f;
            case "ISK":
                return 143.40f;
            case "NOK":
                return 10.7635f;
            case "TRY":
                return 52.9565f;
            case "AUD":
                return 1.6327f;
            case "BRL":
                return 5.8370f;
            case "CAD":
                return 1.5985f;
            case "CNY":
                return 7.9087f;
            case "HKD":
                return 9.1015f;
            case "IDR":
                return 20617.42f;
            case "ILS":
                return 3.3891f;
            case "INR":
                return 112.1815f;
            case "KRW":
                return 1753.17f;
            case "MXN":
                return 20.1520f;
            case "MYR":
                return 4.6219f;
            case "NZD":
                return 1.9880f;
            case "PHP":
                return 71.720f;
            case "SGD":
                return 1.4887f;
            case "THB":
                return 37.951f;
            case "ZAR":
                return 19.3449f;
            default:
                throw new CurrencyExchangeUnavailableException(c.getCurrencyCode());
        }
    }
}
