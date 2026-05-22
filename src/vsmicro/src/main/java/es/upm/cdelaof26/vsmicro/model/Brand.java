package es.upm.cdelaof26.vsmicro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.hateoas.RepresentationModel;

/**
 * Vehicle brand model
 * @author cristopher
 */
@Entity
@Table(schema = "vsdb")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Brand extends RepresentationModel<Brand> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador autoincremental", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;
    
    @Schema(description = "Nombre del tipo de vehículo", pattern = ".{1,128}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    public Brand() { }

    public Brand(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
