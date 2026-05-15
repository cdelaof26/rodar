package es.upm.cdelaof26.pmmicro.repository;

import es.upm.cdelaof26.pmmicro.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Provider repository
 * @author cristopher
 */
@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> { }
