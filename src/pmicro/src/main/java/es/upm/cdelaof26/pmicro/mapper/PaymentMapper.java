package es.upm.cdelaof26.pmicro.mapper;

import es.upm.cdelaof26.pmicro.dto.PaymentDto;
import es.upm.cdelaof26.pmicro.model.Payment;

/**
 * Mapper for Payment and Payment DTO
 * @author cristopher
 */
public class PaymentMapper {
    public Payment toPayment(PaymentDto p) {
        return new Payment(null, p.getReservationId(), p.getUserId(), p.getAmount(), null, null);
    }
}
