package es.upm.cdelaof26.vimicro.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

/**
 * Vehicle DTO model
 * @author cristopher
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleDto extends RepresentationModel<VehicleDto> {
    @Schema(description = "Placa del coche", pattern = ".{7}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String licensePlate;
    
    @Schema(description = "Identificador del inventario por vehículos (vehicleId)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer vehicleId;
    
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Schema(description = "Fecha de inicio del alquiler", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Date startDate;
    
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Schema(description = "Fecha de inicio del alquiler", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Date endDate;
    
    @Schema(description = "Identificador del usuario", pattern = "\\d+", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer userId;
    
    @Schema(description = "Bandera que indica si esta en uso", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean inUse = false;

    
    
    public VehicleDto() { }

    public VehicleDto(String licensePlate, Integer vehicleId, Date startDate, Date endDate, Integer userId, Boolean inUse) {
        this.licensePlate = licensePlate;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.inUse = inUse;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
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
