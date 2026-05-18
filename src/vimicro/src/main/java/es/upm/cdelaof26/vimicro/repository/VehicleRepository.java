package es.upm.cdelaof26.vimicro.repository;

import es.upm.cdelaof26.vimicro.model.Vehicle;
import java.sql.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

/**
 * Vehicle repository
 * @author cristopher
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    // TODO: Use a more portable solution across different DB...
    //
    @NativeQuery(value = "SELECT count(v.license_plate) "
            + "FROM vehicle v LEFT JOIN rent_date rd ON v.license_plate = rd.license_plate "
            + "WHERE v.in_use = 0 AND v.inventory_id = ?1 AND (rd.id IS NULL OR (rd.start_date <= ?2 AND rd.end_date >= ?3));")
    public int findAmountAvailable(Integer vehicleId, Date startDate, Date endDate);
    
    @NativeQuery(value = "SELECT v.license_plate "
            + "FROM vehicle v LEFT JOIN rent_date rd ON v.license_plate = rd.license_plate "
            + "WHERE v.in_use = 0 AND v.inventory_id = ?1 AND (rd.id IS NULL OR (rd.start_date <= ?2 AND rd.end_date >= ?3)) LIMIT 1;")
    public String findOneAvailable(Integer vehicleId, Date startDate, Date endDate);
}
