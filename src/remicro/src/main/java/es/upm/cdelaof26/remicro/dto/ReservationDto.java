package es.upm.cdelaof26.remicro.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payment DTO model
 * @author cristopher
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationDto {
    @Schema(description = "Identificador del vehículo asociado", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer vehicleId;
    
    @Schema(description = "Identificador del usuario asociado", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer userId;
    
    @Schema(description = "Fecha de inicio del alquiler", requiredMode = Schema.RequiredMode.REQUIRED)
    private String startDate;
    
    @Schema(description = "Fecha de termino del alquiler", requiredMode = Schema.RequiredMode.REQUIRED)
    private String endDate;
    
    @Schema(description = "Divisa del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currency;
    
    @Schema(description = "Localización", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String location;
    
    
    
    public ReservationDto() { }

    public ReservationDto(Integer providerId, Integer vehicleId, Integer userId, String startDate, String endDate, String location) {
        this.vehicleId = vehicleId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
