package es.upm.cdelaof26.pmicro.repository;

import es.upm.cdelaof26.pmicro.model.PaymentMethod;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Payment method repository
 * @author cristopher
 */
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    public boolean existsByName(String name);
    
    public Optional<PaymentMethod> findByName(String name);
}
