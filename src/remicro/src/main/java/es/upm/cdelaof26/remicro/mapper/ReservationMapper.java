package es.upm.cdelaof26.remicro.mapper;

import es.upm.cdelaof26.remicro.dto.ReservationDto;
import es.upm.cdelaof26.remicro.model.Reservation;

/**
 * Mapper for Reservation and Reservation DTO
 * @author cristopher
 */
public class ReservationMapper {
    public Reservation toReservation(ReservationDto r) {
        return new Reservation(null, r.getVehicleId(), r.getUserId(), null, null, r.getLocation());
    }
}
