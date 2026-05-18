package es.upm.cdelaof26.vimicro.mapper;

import es.upm.cdelaof26.vimicro.dto.VehicleCreationDto;
import es.upm.cdelaof26.vimicro.model.Vehicle;

/**
 * Mapper for Vehicle classes
 * @author cristopher
 */
public class VehicleMapper {
    public Vehicle toVehicle(VehicleCreationDto v) {
        return new Vehicle(v.getLicensePlate(), null, null, null, false);
    }
    
//    public Vehicle toVehicle(VehicleUpdateDto v) {
//        return new Vehicle(v.getLicensePlate(), null, null, null, false);
//    }
}
