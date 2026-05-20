package es.upm.cdelaof26.vimicro.controller;

import es.upm.cdelaof26.vimicro.dto.VehicleCreationDto;
import es.upm.cdelaof26.vimicro.dto.VehicleUpdateDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    // TODO: everything should be a transaction as across all the endopoints,
    //       operations are performed involving all the repositories
    
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        l.debug("Listing all inventories...");
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }
    
    @GetMapping("/{vehicleId}")
    public ResponseEntity<Integer> getVehicleAvailableAmount(
        @PathVariable int vehicleId, @RequestParam String startDate, @RequestParam String endDate
    ) {
        l.debug(String.format("Listing available vehicles with id %d...", vehicleId));
        
        // Aunque el valor necesario en findAllAvailable es vehicleId, 
        // se busca en inventario (getInventoryById) para validar que exista.
        //
        Inventory i = inventoryService.getInventoryById(vehicleId);
        
        return ResponseEntity.ok(vehicleService.findAllAvailable(i, startDate, endDate));
    }
    
    @PostMapping
    public ResponseEntity<Void> createVehicle(@RequestBody @Valid VehicleCreationDto v) {
        l.debug("Creating new vehicle...");
        return ResponseEntity.created(self.slash(
            vehicleService.createVehicle(v).getInventory().getId()).toUri()
        ).build();
    }
    
    @PutMapping("/{vehicleId}")
    public ResponseEntity<String> updateAvailableVehiclesInDates(
        @PathVariable int vehicleId, @RequestBody @Valid VehicleUpdateDto v
    ) {
        // Pensando a futuro, esta operación debe soportar el devolver vehículos,
        // estas rutas alternativas generan problemas al momento de decidir que
        // campos y combinación de estos deben tener los DTO, así como la 
        // información a retornar.
        
        Inventory i = inventoryService.getInventoryById(vehicleId);
        
        String licensePlate = vehicleService.updateVehicle(v, i);
        
        // return ResponseEntity.noContent().build();
        // El método PUT debería dar como respuesta NO_CONTENT, pero es 
        // necesario darle una placa al usuario una vez haya pagado por la 
        // reserva...
        //
        return ResponseEntity.ok(licensePlate);
    }
}
