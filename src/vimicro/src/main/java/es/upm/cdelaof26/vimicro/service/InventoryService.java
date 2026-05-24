package es.upm.cdelaof26.vimicro.service;

import es.upm.cdelaof26.vimicro.exception.InventoryNotFoundException;
import es.upm.cdelaof26.vimicro.model.Inventory;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import es.upm.cdelaof26.vimicro.repository.InventoryRepository;
import java.sql.Date;

/**
 * Vehicle service
 * @author cristopher
 */
@Service
public class InventoryService {
    private final Logger l = LoggerFactory.getLogger(InventoryService.class);
    
    private final InventoryRepository repository;
    
    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }
    
    public List<Inventory> getAllInventories() {
        return repository.findAll();
    }
    
    public Inventory getInventoryById(int inventoryId) {
        l.debug(String.format("Getting inventory '%d'", inventoryId));
        if (!repository.existsById(inventoryId)) {
            l.error(String.format("Inventory with id %d doesn't exist", inventoryId));
            throw new InventoryNotFoundException(inventoryId);
        }
        
        return repository.findById(inventoryId).get();
    }
    
    public List<Inventory> findAllAvailable(String startDate, String endDate) {
        DateValidationUtil.validateDate(startDate, "startDate");
        DateValidationUtil.validateDate(endDate, "endDate");
        
        return repository.findAmountAvailable(Date.valueOf(startDate), Date.valueOf(endDate));
    }
    
    public Inventory findAvailable(int vehicleId, String startDate, String endDate) {
        DateValidationUtil.validateDate(startDate, "startDate");
        DateValidationUtil.validateDate(endDate, "endDate");
        
        return repository.findAmountAvailable(vehicleId, Date.valueOf(startDate), Date.valueOf(endDate));
    }
}
