package es.upm.cdelaof26.remicro.service;

import es.upm.cdelaof26.remicro.dto.InventoryDto;
import es.upm.cdelaof26.remicro.dto.PaymentDto;
import es.upm.cdelaof26.remicro.dto.PriceDto;
import es.upm.cdelaof26.remicro.dto.ProviderDto;
import es.upm.cdelaof26.remicro.dto.ReservationDto;
import es.upm.cdelaof26.remicro.dto.VehicleDto;
import es.upm.cdelaof26.remicro.exception.InvalidDateException;
import es.upm.cdelaof26.remicro.exception.LendTimeTooLongException;
import es.upm.cdelaof26.remicro.exception.NoInventoryException;
import es.upm.cdelaof26.remicro.mapper.ReservationMapper;
import es.upm.cdelaof26.remicro.model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import es.upm.cdelaof26.remicro.repository.ReservationRepository;

/**
 * Reservation service
 * @author cristopher
 */
@Service
public class ReservationService {
    private final Logger l = LoggerFactory.getLogger(ReservationService.class);
    
    private final ReservationRepository repository;
    private static final ReservationMapper mapper = new ReservationMapper();
    
    private final RestTemplate restTemplate;
    private final String PMMICRO_URL;
    private final String VIMICRO_URL;
    private final String TMICRO_URL;
    private final String VSMICRO_URL;
    private final String PMICRO_URL;

    public ReservationService(
        Environment environment, RestTemplate restTemplate, 
        ReservationRepository repository
    ) {
        this.PMMICRO_URL = environment.getProperty("pmmicro.base-url");
        this.VIMICRO_URL = environment.getProperty("vimicro.base-url");
        this.TMICRO_URL = environment.getProperty("tmicro.base-url");
        this.VSMICRO_URL = environment.getProperty("vsmicro.base-url");
        this.PMICRO_URL = environment.getProperty("pmicro.base-url");
        
        this.restTemplate = restTemplate;
        this.repository = repository;
    }
    
    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }
    
    private void validateDate(String d, String name) {
        if (!d.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            l.error(String.format("Invalid value '%s' for %s", d, name));
            throw new InvalidDateException(d);
        }
    }
    
    // TODO: Move restTemplate calls to another class
    
    private InventoryDto findVehicleAvailability(int vehicleId, String startDate, String endDate) {
        URI uri = UriComponentsBuilder.fromUriString(VIMICRO_URL + "/vehicles")
            .path("/{vehicleId}")
            .queryParam("startDate", "{startDate}")
            .queryParam("endDate", "{endDate}")
            .buildAndExpand(Map.of(
                "vehicleId", vehicleId,
                "startDate", startDate,
                "endDate", endDate))
            .toUri();
        
        ResponseEntity<InventoryDto> resp = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            InventoryDto.class
        );
        
        return resp.getBody();
    }
    
    private ProviderDto findProviderById(int providerId) {
        URI uri = UriComponentsBuilder.fromUriString(PMMICRO_URL + "/providers")
            .path("/{providerId}")
            .buildAndExpand(providerId)
            .toUri();
        
        ResponseEntity<ProviderDto> resp = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            ProviderDto.class
        );
        
        return resp.getBody();
    }
    
    private VehicleDto getVehicle(int vehicleId) {
        URI uri = UriComponentsBuilder.fromUriString(VSMICRO_URL + "/vehicles")
            .path("/{vehicleId}")
            .buildAndExpand(Map.of(
                "vehicleId", vehicleId))
            .toUri();
        
        ResponseEntity<VehicleDto> resp = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            VehicleDto.class
        );
        
        return resp.getBody();
    }
    
    private PriceDto getPricingPerDayInUserCurrency(
        String userCurrency, String providerCurrency, float amount, String location
    ) {
        URI uri = UriComponentsBuilder.fromUriString(TMICRO_URL + "/currencies")
            .path("/{currency}")
            .path("/rates")
            .queryParam("to", "{to}")
            .queryParam("amount", "{amount}")
            .queryParam("location", "{location}")
            .buildAndExpand(Map.of(
                "currency", providerCurrency,
                "to", userCurrency,
                "amount", amount,
                "location", location
            ))
            .toUri();
        
        ResponseEntity<PriceDto> resp = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            HttpEntity.EMPTY,
            PriceDto.class
        );
        
        return resp.getBody();
    }
    
    private void createPaymentEntry(PaymentDto p) {
        restTemplate.exchange(
            UriComponentsBuilder.fromUriString(PMICRO_URL + "/payments").build().toUri(),
            HttpMethod.POST,
            new HttpEntity(p),
            Void.class
        );
    }
    
    public Reservation save(ReservationDto r) {
        l.debug("Running date validations...");
        validateDate(r.getStartDate(), "startDate");
        validateDate(r.getEndDate(), "endDate");
        
        if (r.getLocation() != null)
            l.warn("Overwriting user location to 'Spain'");
        
        r.setLocation("Spain");
        
        Reservation _r = mapper.toReservation(r);
        _r.setStartDate(Date.valueOf(r.getStartDate()));
        _r.setEndDate(Date.valueOf(r.getEndDate()));
        
        long difference = _r.getEndDate().getTime() - _r.getStartDate().getTime();
        if (difference < 0) {
            l.warn("Dates were given backwards, fixing...");
            Date tmp = _r.getStartDate();
            _r.setStartDate(_r.getEndDate());
            _r.setEndDate(tmp);
            difference = Math.abs(difference);
        }
        
        long days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
        if (days > 30) {
            l.error(String.format("Time asked exceeds 30 days policy: asked for %d", days));
            throw new LendTimeTooLongException(days);
        }
        
        int available = findVehicleAvailability(_r.getVehicleId(), r.getStartDate(), r.getEndDate()).amount();
        if (available < 1) {
            l.error(String.format("No enough inventory was found: %d", available));
            throw new NoInventoryException(_r.getVehicleId(), r.getStartDate(), r.getEndDate());
        }
        
        l.info("Retriving vehicle info...");
        VehicleDto v = getVehicle(_r.getVehicleId());
        
        l.info("Retriving provider info...");
        String providerCurrency = findProviderById(v.providerId()).currency();
        
        l.info("Getting up to date pricing...");
        PriceDto p = getPricingPerDayInUserCurrency(
            r.getCurrency(), providerCurrency, v.pricing(), r.getLocation()
        );
        
        l.info("Saving...");
        _r = repository.save(_r);
        
        l.info("Creating payment entry...");
        float total = p.amount() * days;
        createPaymentEntry(new PaymentDto(_r.getId(), _r.getUserId(), total, _r.getVehicleId(), r.getStartDate(), r.getEndDate()));
        
        return _r;
    }
}
