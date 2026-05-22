package es.upm.cdelaof26.vsmicro.repository;

import es.upm.cdelaof26.vsmicro.model.Vehicle;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Vehicle repository
 * @author cristopher
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    public Optional<Vehicle> findByIdAndTypeId(int id, int typeId);
}
