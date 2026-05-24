package es.upm.cdelaof26.remicro.dto;

/**
 * Payment DTO model
 * @author cristopher
 */
public record PaymentDto (
    int reservationId,
    int userId,
    float amount,
    Integer vehicleId,
    String startDate,
    String endDate
) { }
