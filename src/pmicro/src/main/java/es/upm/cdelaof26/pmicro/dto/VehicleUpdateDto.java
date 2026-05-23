package es.upm.cdelaof26.pmicro.dto;

/**
 * Vehicle DTO model
 * @author cristopher
 */
public record VehicleUpdateDto(String startDate, String endDate, Integer userId, Boolean inUse) { }
