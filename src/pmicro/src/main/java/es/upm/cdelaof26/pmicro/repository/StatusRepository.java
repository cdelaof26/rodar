package es.upm.cdelaof26.pmicro.repository;

import es.upm.cdelaof26.pmicro.model.Status;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Payment repository
 * @author cristopher
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
    public Optional<Status> findByName(String name);
    
    public boolean existsByName(String name);
}
