package es.upm.cdelaof26.vimicro.service;

import es.upm.cdelaof26.vimicro.dto.VehicleDto;
import es.upm.cdelaof26.vimicro.exception.VehicleAlreadyExistException;
import es.upm.cdelaof26.vimicro.mapper.VehicleMapper;
import es.upm.cdelaof26.vimicro.model.Inventory;
import es.upm.cdelaof26.vimicro.model.Vehicle;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import es.upm.cdelaof26.vimicro.repository.InventoryRepository;
import es.upm.cdelaof26.vimicro.repository.VehicleRepository;

/**
 * Vehicle service
 * @author cristopher
 */
@Service
public class VehicleService {
    private final Logger l = LoggerFactory.getLogger(VehicleService.class);
    
    private final VehicleRepository vehicleRepository;
    private static final VehicleMapper mapper = new VehicleMapper();

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    
    public void createVehicle(VehicleDto v, Inventory i) {
        Vehicle _v = mapper.toNonDto(v);
        if (vehicleRepository.existsById(_v.getLicensePlate())) {
            l.error(String.format("License plate '%s' is already in use", _v.getLicensePlate()));
            throw new VehicleAlreadyExistException(_v.getLicensePlate());
        }
        
        _v.setInventory(i);
        vehicleRepository.save(_v);
    }
}
