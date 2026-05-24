package es.upm.cdelaof26.pmicro.mapper;

import es.upm.cdelaof26.pmicro.dto.PaymentInDto;
import es.upm.cdelaof26.pmicro.dto.PaymentOutDto;
import es.upm.cdelaof26.pmicro.model.Payment;

/**
 * Mapper for Payment and Payment DTO
 * @author cristopher
 */
public class PaymentMapper {
    public Payment toPayment(PaymentInDto p) {
        return new Payment(null, p.getReservationId(), p.getUserId(), p.getAmount(), null, null);
    }
    
    public PaymentOutDto toPaymentOutDto(Payment p) {
        return new PaymentOutDto(
            p.getReservationId(), p.getUserId(), p.getAmount(), 
            p.getPaymentMethod() == null ? null : p.getPaymentMethod().getName(), 
            p.getStatus() == null ? null : p.getStatus().getName(), null
        );
    }
}
