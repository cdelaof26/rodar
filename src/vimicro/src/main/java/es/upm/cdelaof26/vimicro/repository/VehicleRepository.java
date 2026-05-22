package es.upm.cdelaof26.vimicro.repository;

import es.upm.cdelaof26.vimicro.model.Inventory;
import es.upm.cdelaof26.vimicro.model.Vehicle;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

/**
 * Vehicle repository
 * @author cristopher
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    // Conditional:
    //  The conditional checks for overlapped dates
    // 
    //   rd.start  start     end     rd.end  :  query dates are in row dates
    //   start     rd.start  rd.end  end     :  row dates are in query dates
    //   rd.start  start     rd.end  end     :  start date is inside row dates
    //   start     rd.start  end     rd.end  :  end date is inside row dates
    
    // TODO: Use a more portable solution across different DB...
    //
    @NativeQuery(value = "SELECT v.inventory_id AS id, count(v.license_plate) AS amount "
            + "FROM vehicle v LEFT JOIN rent_date rd ON v.license_plate = rd.license_plate "
            + "WHERE rd.id IS NULL OR NOT "
            + "(rd.start_date <= ?1 AND rd.end_date >= ?2 AND "
            + "rd.start_date >= ?1 AND rd.end_date <= ?2 AND "
            + "rd.start_date <= ?1 AND rd.end_date <= ?2 AND "
            + "rd.start_date >= ?1 AND rd.end_date >= ?2)"
            + "GROUP BY v.inventory_id;")
    public List<Inventory> findAmountAvailable(Date startDate, Date endDate);
    
    @NativeQuery(value = "SELECT v.license_plate "
            + "FROM vehicle v LEFT JOIN rent_date rd ON v.license_plate = rd.license_plate "
            + "WHERE v.in_use = 0 AND v.inventory_id = ?1 AND "
            + "(rd.id IS NULL OR NOT "
            + "(rd.start_date <= ?2 AND rd.end_date >= ?3 AND "
            + "rd.start_date >= ?2 AND rd.end_date <= ?3 AND "
            + "rd.start_date <= ?2 AND rd.end_date <= ?3 AND "
            + "rd.start_date >= ?2 AND rd.end_date >= ?3))"
            + "LIMIT 1;")
    public String findOneAvailable(Integer vehicleId, Date startDate, Date endDate);
}
