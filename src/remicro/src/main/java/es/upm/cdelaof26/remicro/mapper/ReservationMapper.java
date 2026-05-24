package es.upm.cdelaof26.remicro.mapper;

import es.upm.cdelaof26.remicro.dto.ReservationDto;
import es.upm.cdelaof26.remicro.model.Reservation;

/**
 * Mapper for Reservation and Reservation DTO
 * @author cristopher
 */
public class ReservationMapper {
    public Reservation toReservation(ReservationDto p) {
        return new Reservation(null, p.getVehicleId(), p.getUserId(), null, null, p.getLocation());
    }
}
