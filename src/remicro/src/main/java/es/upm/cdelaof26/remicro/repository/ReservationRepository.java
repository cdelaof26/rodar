package es.upm.cdelaof26.remicro.repository;

import es.upm.cdelaof26.remicro.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Reservation repository
 * @author cristopher
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> { }
