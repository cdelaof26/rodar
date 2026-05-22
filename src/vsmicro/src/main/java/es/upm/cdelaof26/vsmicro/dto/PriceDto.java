package es.upm.cdelaof26.vsmicro.dto;

/**
 * Price DTO model
 * @author cristopher
 */
public class PriceDto {
    private final String currency;
    private final float amount;

    public PriceDto() {
        this.currency = null;
        this.amount = 0;
    }

    public PriceDto(String currency, float amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public float getAmount() {
        return amount;
    }
}
