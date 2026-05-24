package es.upm.cdelaof26.vimicro.repository;

import es.upm.cdelaof26.vimicro.model.Inventory;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

/**
 * Inventory repository
 * @author cristopher
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    @NativeQuery(value = "SELECT v.inventory_id AS id, count(v.license_plate) AS amount "
            + "FROM vehicle v LEFT JOIN rent_date rd ON v.license_plate = rd.license_plate "
            + "WHERE rd.id IS NULL OR NOT "
            + "(rd.start_date <= ?1 AND rd.end_date >= ?2 AND "
            + "rd.start_date >= ?1 AND rd.end_date <= ?2 AND "
            + "rd.start_date <= ?1 AND rd.end_date <= ?2 AND "
            + "rd.start_date >= ?1 AND rd.end_date >= ?2)"
            + "GROUP BY v.inventory_id;")
    public List<Inventory> findAmountAvailable(Date startDate, Date endDate);
    
    @NativeQuery(value = "SELECT v.inventory_id AS id, count(v.license_plate) AS amount "
            + "FROM vehicle v LEFT JOIN rent_date rd ON v.license_plate = rd.license_plate "
            + "WHERE v.inventory_id = ?1 AND (rd.id IS NULL OR NOT "
            + "(rd.start_date <= ?2 AND rd.end_date >= ?3 AND "
            + "rd.start_date >= ?2 AND rd.end_date <= ?3 AND "
            + "rd.start_date <= ?2 AND rd.end_date <= ?3 AND "
            + "rd.start_date >= ?2 AND rd.end_date >= ?3));")
    public Inventory findAmountAvailable(int vehicleId, Date startDate, Date endDate);
}
