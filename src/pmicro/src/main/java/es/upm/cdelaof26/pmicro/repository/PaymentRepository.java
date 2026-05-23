package es.upm.cdelaof26.pmicro.repository;

import es.upm.cdelaof26.pmicro.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Payment repository
 * @author cristopher
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    public boolean existsByReservationId(Integer id);
}
