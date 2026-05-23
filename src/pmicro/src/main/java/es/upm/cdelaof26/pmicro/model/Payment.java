package es.upm.cdelaof26.pmicro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.springframework.hateoas.RepresentationModel;

/**
 * Payment model
 * @author cristopher
 */
@Entity
@Table(schema = "pdb", uniqueConstraints = @UniqueConstraint(columnNames = {"reservation_id"}))
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment extends RepresentationModel<Payment> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador autoincremental", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;
    
    @Schema(description = "Identificador de la reservación asociada", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer reservationId;
    
    @Schema(description = "Identificador del usuario asociado", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer userId;
    
    @Schema(description = "Importe", requiredMode = Schema.RequiredMode.REQUIRED)
    private float amount;
    
    @OneToOne
    @JoinColumn(name = "payment_method_id", referencedColumnName = "id")
    @Schema(description = "Método de pago", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private PaymentMethod paymentMethod;
    
    @OneToOne
    @JoinColumn(name = "status", referencedColumnName = "id")
    @Schema(description = "Estado del pago", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Status status;
    
    
    public Payment() { }

    public Payment(Integer id, Integer reservationId, Integer userId, float amount, PaymentMethod paymentMethod, Status status) {
        this.id = id;
        this.reservationId = reservationId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
