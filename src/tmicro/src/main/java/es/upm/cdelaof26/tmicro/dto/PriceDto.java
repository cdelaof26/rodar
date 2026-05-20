package es.upm.cdelaof26.tmicro.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Price DTO model
 * @author cristopher
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceDto {
    @Schema(description = "Nombre de la divisa")
    private String currency;
    
    @Schema(description = "Cantidad")
    private float amount;
    
    
    public PriceDto() { }

    public PriceDto(String currency, float amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
