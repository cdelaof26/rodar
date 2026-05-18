package es.upm.cdelaof26.vimicro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.hateoas.RepresentationModel;

/**
 * Inventory model
 * @author cristopher
 */
@Entity
@Table(schema = "vidb")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inventory extends RepresentationModel<Inventory> {
    @Id
    @Schema(description = "Identificador del inventario por vehículos (vehicleId)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;
    
    private int amount = 0;

    
    
    public Inventory() { }

    public Inventory(Integer id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }
}
