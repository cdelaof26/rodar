package es.upm.cdelaof26.vimicro.repository;

import es.upm.cdelaof26.vimicro.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Inventory repository
 * @author cristopher
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> { }
