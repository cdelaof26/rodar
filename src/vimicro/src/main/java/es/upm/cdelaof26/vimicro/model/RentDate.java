package es.upm.cdelaof26.vimicro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

/**
 * Dates model
 * @author cristopher
 */
@Entity
@Table(schema = "RentDate")
public class RentDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador autoincremental", requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;
    
    @Schema(description = "Placa del vehículo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String licensePlate;
    
    @Schema(description = "Fecha de inicio del periodo de alquiler", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date startDate;
    
    @Schema(description = "Fecha de fin del periodo de alquiler", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date endDate;

    
    
    public RentDate() { }

    public RentDate(int id, String licensePlate, Date startDate, Date endDate) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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
}
