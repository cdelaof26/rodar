package es.upm.cdelaof26.vimicro.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

/**
 * Vehicle DTO model
 * @author cristopher
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleCreationDto extends RepresentationModel<VehicleCreationDto> {
    @Schema(description = "Placa del coche", pattern = ".{7}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String licensePlate;
    
    @Schema(description = "Identificador del inventario por vehículos (vehicleId)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer vehicleId;

    
    
    public VehicleCreationDto() { }

    public VehicleCreationDto(String licensePlate, Integer vehicleId) {
        this.licensePlate = licensePlate;
        this.vehicleId = vehicleId;
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
}
