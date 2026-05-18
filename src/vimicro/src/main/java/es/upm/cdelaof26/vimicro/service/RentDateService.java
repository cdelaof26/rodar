package es.upm.cdelaof26.vimicro.service;

import es.upm.cdelaof26.vimicro.model.RentDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import es.upm.cdelaof26.vimicro.repository.RentDateRepository;
import java.sql.Date;

/**
 * Vehicle service
 * @author cristopher
 */
@Service
public class RentDateService {
    private final Logger l = LoggerFactory.getLogger(RentDateService.class);
    
    private final RentDateRepository repository;
    
    public RentDateService(RentDateRepository repository) {
        this.repository = repository;
    }
    
    public RentDate createRentDate(String licensePlate, Date startDate, Date endDate) {
        RentDate rd = new RentDate();
        rd.setLicensePlate(licensePlate);
        rd.setStartDate(startDate);
        rd.setEndDate(endDate);
        return save(rd);
    }
    
    public RentDate save(RentDate rd) {
        return repository.save(rd);
    }
}
