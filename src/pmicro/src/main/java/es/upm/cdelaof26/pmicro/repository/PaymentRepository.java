package es.upm.cdelaof26.pmicro.repository;

import es.upm.cdelaof26.pmicro.model.Payment;
import es.upm.cdelaof26.pmicro.model.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Payment repository
 * @author cristopher
 */
//@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    public boolean existsByReservationId(Integer id);
    
    public List<Payment> findAllByUserIdAndStatus(int userId, Status status);
}
