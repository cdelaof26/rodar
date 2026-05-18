package es.upm.cdelaof26.vimicro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import org.springframework.hateoas.RepresentationModel;

/**
 * Vehicle model
 * @author cristopher
 */
@Entity
@Table(schema = "vidb")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle extends RepresentationModel<Vehicle> {
    @Id
    @Schema(description = "Placa del coche", pattern = ".{7}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String licensePlate;
    
    
    @OneToMany
    @JoinColumn(name = "licensePlate", referencedColumnName = "licensePlate")
    @Schema(description = "Vehiculos de renta", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RentDate> dates;
    
    @OneToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    @Schema(description = "Inventario al que pertenece", requiredMode = Schema.RequiredMode.REQUIRED)
    private Inventory inventory;
    
    @Schema(description = "Identificador del usuario", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer userId;
    
    @Schema(description = "Bandera que indica si esta en uso", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean inUse;

    
    
    public Vehicle() { }

    public Vehicle(String licensePlate, List<RentDate> dates, Inventory inventory, Integer userId, Boolean inUse) {
        this.licensePlate = licensePlate;
        this.dates = dates;
        this.inventory = inventory;
        this.userId = userId;
        this.inUse = inUse;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean isInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }
}
