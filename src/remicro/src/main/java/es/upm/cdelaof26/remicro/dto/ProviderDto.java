package es.upm.cdelaof26.remicro.dto;

/**
 * Provider DTO model
 * @author cristopher
 */
public record ProviderDto (
    Integer id,
    String name,
    String company,
    String email,
    String phone,
    String location,
    String currency
) { }
