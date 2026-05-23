package es.upm.cdelaof26.pmicro.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Payment DTO model
 * @author cristopher
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDto {
    @Schema(description = "Identificador de la reservación asociada", requiredMode = Schema.RequiredMode.REQUIRED)
    private int reservationId;
    
    @Schema(description = "Identificador del usuario asociado", requiredMode = Schema.RequiredMode.REQUIRED)
    private int userId;
    
    @Schema(description = "Importe", requiredMode = Schema.RequiredMode.REQUIRED)
    private float amount;
    
    @Schema(description = "Método de pago", allowableValues = {"cash", "debit_card", "credit_card"}, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String paymentMethod;
    
    @Schema(description = "Estado del pago", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String status;
    
    @Schema(description = "Identificador del vehículo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer vehicleId;
    
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Schema(description = "Fecha de inicio del alquiler", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String startDate;
    
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Schema(description = "Fecha de inicio del alquiler", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String endDate;
    
    
    public PaymentDto() { }

    public PaymentDto(
        int reservationId, int userId, float amount, String paymentMethod, 
        String status, Integer vehicleId, String startDate, String endDate
    ) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
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
}
