package es.upm.cdelaof26.remicro.dto;

/**
 * Vehicle DTO model
 * @author cristopher
 */
public record VehicleDto (
    Integer id,
    int providerId,
    float pricing,
    VehiclePropertyDto type,
    VehiclePropertyDto model,
    VehiclePropertyDto brand
) {}
