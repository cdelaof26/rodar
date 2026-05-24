package es.upm.cdelaof26.remicro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;
import org.springframework.hateoas.RepresentationModel;

/**
 * Reservation model
 * @author cristopher
 */
@Entity
@Table(schema = "redb")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reservation extends RepresentationModel<Reservation> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador autoincremental", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;
    
    @Schema(description = "Identificador del vehículo asociado", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer vehicleId;
    
    @Schema(description = "Identificador del usuario asociado", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer userId;
    
    @Schema(description = "Fecha de inicio del alquiler", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date startDate;
    
    @Schema(description = "Fecha de termino del alquiler", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date endDate;
    
    @Schema(description = "Localización", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String location;
    
    
    public Reservation() { }

    public Reservation(Integer id, Integer vehicleId, Integer userId, Date startDate, Date endDate, String location) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
