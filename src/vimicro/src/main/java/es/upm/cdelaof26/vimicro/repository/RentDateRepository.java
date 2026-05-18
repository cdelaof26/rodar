package es.upm.cdelaof26.vimicro.repository;

import es.upm.cdelaof26.vimicro.model.RentDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RentDate repository
 * @author cristopher
 */
@Repository
public interface RentDateRepository extends JpaRepository<RentDate, Integer> { }
