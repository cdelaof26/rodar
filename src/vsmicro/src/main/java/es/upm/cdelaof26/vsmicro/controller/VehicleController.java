package es.upm.cdelaof26.vsmicro.controller;

import es.upm.cdelaof26.vsmicro.service.VehicleService;
import java.util.List;
import es.upm.cdelaof26.vsmicro.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Vehicle REST controller
 * @author cristopher
 */
@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private static final Logger l = LoggerFactory.getLogger(VehicleController.class);
    
    private final WebMvcLinkBuilder self = WebMvcLinkBuilder.linkTo(VehicleController.class);
    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }
    
    @GetMapping()
    public ResponseEntity<List<Vehicle>> getVehicles(
        @RequestParam(required = false) String location, @RequestParam String startDate, 
        @RequestParam String endDate, @RequestParam(required = false) List<String> types, 
        @RequestParam(required = false) List<Float> prices, @RequestParam String currency
    ) {
        // TODO: Location shouldn't be optional, but since no geolocation stuff 
        // will be implemented, then there's no reason for it to be there.
        
        l.debug("Getting all vehicles given the parameters,");
        l.debug("location = " + location);
        l.debug("startDate = " + startDate);
        l.debug("endDate = " + endDate);
        l.debug("types = " + types);
        l.debug("prices = " + prices);
        l.debug("currency = " + currency);
        return ResponseEntity.ok(
            service.getAllVehicles(location, startDate, endDate, types, prices, currency)
        );
    }
    
    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable int vehicleId) {
        l.debug(String.format("Getting vehicle %d...", vehicleId));
        return ResponseEntity.ok(service.getVehicle(vehicleId));
    }
}
