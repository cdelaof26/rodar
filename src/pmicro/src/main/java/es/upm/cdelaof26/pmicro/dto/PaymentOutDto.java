package es.upm.cdelaof26.pmicro.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payment DTO model
 * @author cristopher
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentOutDto {
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
    
    @Schema(description = "Reservación completa asociada", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ReservationDto reservation;
    
    
    public PaymentOutDto() { }

    public PaymentOutDto(
        int reservationId, int userId, float amount, String paymentMethod, 
        String status, ReservationDto reservation
    ) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.reservation = reservation;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public ReservationDto getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDto reservation) {
        this.reservation = reservation;
    }
}
