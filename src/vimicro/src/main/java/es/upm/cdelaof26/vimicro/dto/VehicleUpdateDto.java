package es.upm.cdelaof26.vimicro.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

/**
 * Vehicle DTO model
 * @author cristopher
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleUpdateDto extends RepresentationModel<VehicleUpdateDto> {
//    @Schema(description = "Placa del coche", pattern = ".{7}", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
//    private String licensePlate;
    
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Schema(description = "Fecha de inicio del alquiler", requiredMode = Schema.RequiredMode.REQUIRED)
    private String startDate;
    
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Schema(description = "Fecha de inicio del alquiler", requiredMode = Schema.RequiredMode.REQUIRED)
    private String endDate;
    
    @Schema(description = "Identificador del usuario", pattern = "\\d+", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer userId;
    
    @Schema(description = "Bandera que indica si esta en uso", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean inUse = false;

    
    
    public VehicleUpdateDto() { }

    public VehicleUpdateDto(/*String licensePlate, Integer vehicleId, */ String startDate, String endDate, Integer userId, Boolean inUse) {
//        this.licensePlate = licensePlate;
//        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.inUse = inUse;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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
