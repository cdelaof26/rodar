package es.upm.cdelaof26.vsmicro.service;

import es.upm.cdelaof26.vsmicro.dto.InventoryDto;
import es.upm.cdelaof26.vsmicro.dto.PriceDto;
import es.upm.cdelaof26.vsmicro.dto.ProviderDto;
import es.upm.cdelaof26.vsmicro.exception.InvalidDateException;
import es.upm.cdelaof26.vsmicro.exception.InvalidPricingRangeException;
import es.upm.cdelaof26.vsmicro.exception.ObjectNotFoundException;
import es.upm.cdelaof26.vsmicro.model.Type;
import java.util.List;
import es.upm.cdelaof26.vsmicro.model.Vehicle;
import es.upm.cdelaof26.vsmicro.repository.TypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import es.upm.cdelaof26.vsmicro.repository.VehicleRepository;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * Vehicle service
 * @author cristopher
 */
@Service
public class VehicleService {
    private final Logger l = LoggerFactory.getLogger(VehicleService.class);
    
    private final RestTemplate restTemplate;
    private final VehicleRepository vehicleRepository;
    private final TypeRepository typeRepository;
    
    private final String PMMICRO_URL;
    private final String VIMICRO_URL;
    private final String TMICRO_URL;
    
    public VehicleService(
        Environment environment, RestTemplate restTemplate, 
        VehicleRepository vehicleRepository, TypeRepository typeRepository
    ) {
        this.restTemplate = restTemplate;
        this.vehicleRepository = vehicleRepository;
        this.typeRepository = typeRepository;
        
        this.PMMICRO_URL = environment.getProperty("pmmicro.base-url");
        this.VIMICRO_URL = environment.getProperty("vimicro.base-url");
        this.TMICRO_URL = environment.getProperty("tmicro.base-url");
    }
    
    private void validateDate(String d, String name) {
        // Code shamelessly stolen from vimicro
        if (!d.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            l.error(String.format("Invalid value '%s' for %s", d, name));
            throw new InvalidDateException(d);
        }
    }
    
    private List<Type> stringListToTypeList(List<String> ls) {
        List<Type> lt = new ArrayList<>();
        
        // TODO: There's probably a better way to do this
        for (String s : ls) {
            if (!typeRepository.existsByName(s)) {
                l.error(String.format("Vehicle type '%s' not found", s));
                throw new ObjectNotFoundException("tipo", "nombre", s);
            }
            
            lt.add(typeRepository.findByName(s));
        }
        
        return lt;
    }
    
    private void validatePricing(List<Float> prices) {
        if (prices != null && !prices.isEmpty()) {
            if (prices.size() > 2) {
                l.error(String.format("%d prices were given", prices.size()));
                throw new InvalidPricingRangeException();
            }
            if (prices.get(0) < 0 || prices.size() > 1 && prices.get(1) < 0) {
                l.error(String.format("Negative prices were given: %f, %f", prices.get(0), prices.get(1)));
                throw new InvalidPricingRangeException();
            }
            
            if (prices.size() != 2)
                return;
            
            if (prices.get(0) > prices.get(1)) {
                float tmp = prices.getFirst();
                prices.set(0, prices.getLast());
                prices.set(1, tmp);
            }
        }
    }
    
    private List<InventoryDto> findVehiclesByDate(String startDate, String endDate) {
        URI uri = UriComponentsBuilder.fromUriString(VIMICRO_URL + "/vehicles")
            .queryParam("startDate", "{startDate}")
            .queryParam("endDate", "{endDate}")
            .buildAndExpand(Map.of(
                "startDate", startDate,
                "endDate", endDate))
            .toUri();
        
        ParameterizedTypeReference<List<InventoryDto>> typeRef = new ParameterizedTypeReference<List<InventoryDto>>() {};
        ResponseEntity<List<InventoryDto>> resp = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            new HttpEntity<>(new HttpHeaders()),
            typeRef
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
            new HttpEntity<>(new HttpHeaders()),
            ProviderDto.class
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
            new HttpEntity<>(new HttpHeaders()),
            PriceDto.class
        );
        
        return resp.getBody();
    }
    
    public List<Vehicle> getAllVehicles(
        String location, String startDate, String endDate, List<String> types, 
        List<Float> prices, String currency
    ) {
        if (location != null)
            l.warn(String.format("Overwriting location value %s for 'Spain'", location));
        
        location = "Spain";
        
        validateDate(startDate, "startDate");
        validateDate(endDate, "endDate");
        
        List<Type> rtypes = null;
        if (types != null)
            rtypes = stringListToTypeList(types);
        
        validatePricing(prices);
        
        List<InventoryDto> availableVehicles = findVehiclesByDate(startDate, endDate);
        
        List<Vehicle> vehicles = new ArrayList<>();
        
        // TODO: There must be a best way to do it
        //
        if (rtypes != null) {
            for (InventoryDto av : availableVehicles)
                for (Type t : rtypes) {
                    Optional<Vehicle> ov = vehicleRepository.findByIdAndTypeId(av.getId(), t.getId());
                    if (ov.isPresent())
                        vehicles.add(ov.get());
                }
        } else {
            // Get should not be problem unless a registry in any 
            // database is out of sync, which IRL is probably anything but 
            // guaranteed...
            //
            for (InventoryDto av : availableVehicles)
                vehicles.add(vehicleRepository.findById(av.getId()).get());
        }
        
        int i = 0;
        while (i < vehicles.size()) {
            Vehicle v = vehicles.get(i);
            String providerCurrency = findProviderById(v.getProviderId()).getCurrency();
            PriceDto p = getPricingPerDayInUserCurrency(
                currency, providerCurrency, v.getPricing(), location
            );
            
            v.setPricing(p.getAmount());
            
            // If theres no price limits
            if (prices == null) {
                i++;
                continue;
            }
            
            // If a maximum was provided
            if (prices.size() == 1) {
                if (prices.get(0) < v.getPricing()) {
                    vehicles.remove(i);
                    continue;
                }
                
                i++;
                continue;
            }
            
            // If a price range was provided
            if (prices.get(0) > v.getPricing() || prices.get(1) < v.getPricing()) {
                vehicles.remove(i);
                continue;
            }
            
            i++;
        }
    
        return vehicles;
    }
    
    public Vehicle getVehicle(int vehicleId) {
        if (!vehicleRepository.existsById(vehicleId)) {
            l.error(String.format("Vehicle with id %s not found", vehicleId));
            throw new ObjectNotFoundException("vehículo", vehicleId);
        }
        
        return vehicleRepository.findById(vehicleId).get();
    }
}
