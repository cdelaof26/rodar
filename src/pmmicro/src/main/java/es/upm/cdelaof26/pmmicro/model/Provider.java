package es.upm.cdelaof26.pmmicro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;

/**
 * Provider model
 * @author cristopher
 */
@Entity
@Table(schema = "pmdb", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "phone"}))
@JsonIgnoreProperties(ignoreUnknown = true)
public class Provider extends RepresentationModel<Provider> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador autoincremental", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;
    
    @NotEmpty(message = "El nombre completo es obligatorio")
    @Schema(description = "Nombre completo", pattern = ".{1,128}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    @Schema(description = "Compañía", pattern = ".{0,128}", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String company;
    
    @Email(message = "El correo es obligatorio")
    @Schema(description = "Correo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    
    @NotEmpty(message = "El número de teléfono es obligatorio")
    @Schema(description = "Número de teléfono", pattern = "\\d{10}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;
    
    @NotEmpty(message = "La localización es obligatoria")
    @Schema(description = "Localización", pattern = ".{1,128}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String location;
    
    @NotEmpty(message = "Se requiere de una divisa")
    @Schema(description = "Divisa", pattern = ".{1,4}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currency;

    
    
    public Provider() { }

    public Provider(Integer id, String name, String company, String email, String phone, String location, String currency) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.currency = currency;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
