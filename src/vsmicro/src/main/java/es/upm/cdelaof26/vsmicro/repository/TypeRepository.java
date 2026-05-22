package es.upm.cdelaof26.vsmicro.repository;

import es.upm.cdelaof26.vsmicro.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Type repository
 * @author cristopher
 */
@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    public boolean existsByName(String name);
    public Type findByName(String name);
}
