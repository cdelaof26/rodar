package es.upm.cdelaof26.vsmicro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.springframework.hateoas.RepresentationModel;

/**
 * Vehicle model
 * @author cristopher
 */
@Entity
@Table(schema = "vsdb")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle extends RepresentationModel<Vehicle> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador autoincremental", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;
    
    @Schema(description = "Identificador del proveedor", requiredMode = Schema.RequiredMode.REQUIRED)
    private int providerId;
    
    @Schema(description = "Precio del alquiler por día", requiredMode = Schema.RequiredMode.REQUIRED)
    private float pricing;
    
    @OneToOne
    @Schema(description = "Tipo de vehículo", requiredMode = Schema.RequiredMode.REQUIRED)
    private Type type;
    
    @OneToOne
    @Schema(description = "Modelo del vehículo", requiredMode = Schema.RequiredMode.REQUIRED)
    private Model model;
    
    @OneToOne
    @Schema(description = "Marca del vehículo", requiredMode = Schema.RequiredMode.REQUIRED)
    private Brand brand;
    

    
    
    public Vehicle() { }

    public Vehicle(Integer id, int providerId, float pricing, Type type, Model model, Brand brand) {
        this.id = id;
        this.providerId = providerId;
        this.pricing = pricing;
        this.type = type;
        this.model = model;
        this.brand = brand;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public float getPricing() {
        return pricing;
    }

    public void setPricing(float pricing) {
        this.pricing = pricing;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
