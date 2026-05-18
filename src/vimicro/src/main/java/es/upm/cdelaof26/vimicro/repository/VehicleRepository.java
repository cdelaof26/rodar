package es.upm.cdelaof26.vimicro.repository;

import es.upm.cdelaof26.vimicro.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Vehicle repository
 * @author cristopher
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> { }
