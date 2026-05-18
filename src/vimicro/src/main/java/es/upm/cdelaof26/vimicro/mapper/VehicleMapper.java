package es.upm.cdelaof26.vimicro.mapper;

import es.upm.cdelaof26.vimicro.dto.VehicleDto;
import es.upm.cdelaof26.vimicro.model.Vehicle;

/**
 * Mapper for Vehicle classes
 * @author cristopher
 */
public class VehicleMapper {
    public Vehicle toNonDto(VehicleDto v) {
        return new Vehicle(
            v.getLicensePlate(), null, null, v.getUserId(), 
            v.isInUse() != null ? v.isInUse() : false
        );
    }
}
