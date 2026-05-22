package es.upm.cdelaof26.vsmicro.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * InventoryDto model
 * @author cristopher
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryDto {
    private final int id;
    private final int amount;

    public InventoryDto() {
        this.id = 0;
        this.amount = 0;
    }

    public InventoryDto(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }
}
