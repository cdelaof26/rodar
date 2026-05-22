package es.upm.cdelaof26.vsmicro.dto;

/**
 * ProviderDto model
 * @author cristopher
 */
public class ProviderDto {
    private final Integer id; 
    private final String name; 
    private final String company; 
    private final String email; 
    private final String phone; 
    private final String location; 
    private final String currency;

    public ProviderDto() {
        this.id = null;
        this.name = null;
        this.company = null;
        this.email = null;
        this.phone = null;
        this.location = null;
        this.currency = null;
    }

    public ProviderDto(Integer id, String name, String company, String email, String phone, String location, String currency) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.currency = currency;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public String getCurrency() {
        return currency;
    }
}
