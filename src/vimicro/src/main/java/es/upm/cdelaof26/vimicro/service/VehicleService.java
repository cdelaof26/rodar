package es.upm.cdelaof26.vimicro.service;

import es.upm.cdelaof26.vimicro.dto.VehicleCreationDto;
import es.upm.cdelaof26.vimicro.dto.VehicleUpdateDto;
import es.upm.cdelaof26.vimicro.exception.InvalidDateException;
import es.upm.cdelaof26.vimicro.exception.MissingFieldException;
import es.upm.cdelaof26.vimicro.exception.NoAvailableVehiclesException;
import es.upm.cdelaof26.vimicro.exception.VehicleAlreadyExistException;
import es.upm.cdelaof26.vimicro.exception.UnsupportedException;
import es.upm.cdelaof26.vimicro.mapper.VehicleMapper;
import es.upm.cdelaof26.vimicro.model.Inventory;
import es.upm.cdelaof26.vimicro.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import es.upm.cdelaof26.vimicro.repository.VehicleRepository;
import java.sql.Date;

/**
 * Vehicle service
 * @author cristopher
 */
@Service
public class VehicleService {
    private final Logger l = LoggerFactory.getLogger(VehicleService.class);
    
    private final VehicleRepository vehicleRepository;
    private final RentDateService rentDateService;
    private static final VehicleMapper mapper = new VehicleMapper();

    public VehicleService(VehicleRepository vehicleRepository, RentDateService rentDateService) {
        this.vehicleRepository = vehicleRepository;
        this.rentDateService = rentDateService;
    }
    
    public Vehicle createVehicle(VehicleCreationDto v) {
        if (v.getVehicleId() == null) {
            l.error("Missing vehicleId for new inventory entry");
            throw new MissingFieldException("vehicleId");
        }
        
        Vehicle _v = mapper.toVehicle(v);
        if (vehicleRepository.existsById(_v.getLicensePlate())) {
            l.error(String.format("License plate '%s' is already in use", _v.getLicensePlate()));
            throw new VehicleAlreadyExistException(_v.getLicensePlate());
        }
        
        _v = vehicleRepository.save(_v);
        l.debug("Vehicle created successfully");
        return _v;
    }
    
    private void validateDate(String d, String name) {
        if (!d.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            l.error(String.format("Invalid value '%s' for %s", d, name));
            throw new InvalidDateException(d);
        }
    }
    
    public int findAllAvailable(Inventory i, String startDate, String endDate) {
        validateDate(startDate, "startDate");
        validateDate(endDate, "endDate");
        
        return vehicleRepository.findAmountAvailable(i.getId(), Date.valueOf(startDate), Date.valueOf(endDate));
    }
    
    public String updateVehicle(VehicleUpdateDto v, Inventory i) {
        if (!v.isInUse()) {
            l.error("Update vehicle inUse flag is set to false: OPERATION RETURN LENT VEHICLE IS UNSUPPORTED");
            throw new UnsupportedException();
        }
        
        if (i.getAmount() == 0) {
            l.error(String.format("No vehicles found for update in inventory %d", i.getId()));
            throw new NoAvailableVehiclesException(i.getId(), v.getStartDate(), v.getEndDate());
        }
        
        validateDate(v.getStartDate(), "startDate");
        validateDate(v.getEndDate(), "endDate");
        
        Date startDate = Date.valueOf(v.getStartDate());
        Date endDate = Date.valueOf(v.getEndDate());
        
        String licensePlate = vehicleRepository.findOneAvailable(i.getId(), startDate, endDate);
        if (licensePlate == null) {
            l.error(String.format("No vehicles found for update in inventory %d", i.getId()));
            throw new NoAvailableVehiclesException(i.getId(), v.getStartDate(), v.getEndDate());
        }
        
        // idk how to make findOneAvailable return the whole vehicle...
        Vehicle _v = vehicleRepository.findById(licensePlate).get();
        
        _v.setInUse(v.isInUse());
        _v.setUserId(v.getUserId());
        
        rentDateService.createRentDate(_v.getLicensePlate(), startDate, endDate);
        vehicleRepository.save(_v);
        
        l.debug(String.format("Vehicle '%s' update succeed", _v.getLicensePlate()));
        
        return _v.getLicensePlate();
    }
}
