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
    
    private final InventoryRepository inventoryRepository;
    
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }
    
    public Inventory getInventoryById(int inventoryId) {
        if (!inventoryRepository.existsById(inventoryId)) {
            l.error(String.format("Inventory with id %d doesn't exist", inventoryId));
            throw new InventoryNotFoundException(inventoryId);
        }
        
        return inventoryRepository.findById(inventoryId).get();
    }
    
    public Inventory newInventory(int inventoryId) {
        return inventoryRepository.save(new Inventory(inventoryId, 0));
    }
}
