package es.upm.cdelaof26.vimicro.service;

import es.upm.cdelaof26.vimicro.exception.InventoryNotFoundException;
import es.upm.cdelaof26.vimicro.model.Inventory;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import es.upm.cdelaof26.vimicro.repository.InventoryRepository;

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
    
    public Inventory newInventory(int inventoryId) {
        l.debug(String.format("Creating new inventory...", inventoryId));
        return repository.save(new Inventory(inventoryId, 0));
    }
    
    public Inventory getOrCreateInventory(int inventoryId) {
        try {
            return getInventoryById(inventoryId);
        } catch (InventoryNotFoundException ex) {
            return newInventory(inventoryId);
        }
    }
}
