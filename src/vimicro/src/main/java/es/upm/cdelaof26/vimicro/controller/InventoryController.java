package es.upm.cdelaof26.vimicro.controller;

import es.upm.cdelaof26.vimicro.dto.VehicleDto;
import es.upm.cdelaof26.vimicro.exception.InventoryNotFoundException;
import es.upm.cdelaof26.vimicro.model.Inventory;
import es.upm.cdelaof26.vimicro.service.InventoryService;
import es.upm.cdelaof26.vimicro.service.VehicleService;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Vehicle REST controller
 * @author cristopher
 */
@RestController
@RequestMapping("/vehicles")
public class InventoryController {
    private final Logger l = LoggerFactory.getLogger(InventoryController.class);
    
    private final WebMvcLinkBuilder self = WebMvcLinkBuilder.linkTo(InventoryController.class);
    private final InventoryService inventoryService;
    private final VehicleService vehicleService;

    public InventoryController(InventoryService inventoryService, VehicleService vehicleService) {
        this.inventoryService = inventoryService;
        this.vehicleService = vehicleService;
    }
    
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllProviders() {
        l.debug("Listing all inventories...");
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }
    
    @PostMapping
    public ResponseEntity<Void> createVehicle(@RequestBody @Valid VehicleDto v) {
        Inventory i;
        try {
            i = inventoryService.getInventoryById(v.getVehicleId());
        } catch (InventoryNotFoundException ex) {
            i = inventoryService.newInventory(v.getVehicleId());
        }
        
        vehicleService.createVehicle(v, i);
        return ResponseEntity.created(self.slash(i.getId()).toUri()).build();
    }
}
