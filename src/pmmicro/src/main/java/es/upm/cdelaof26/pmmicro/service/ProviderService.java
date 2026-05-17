package es.upm.cdelaof26.pmmicro.service;

import es.upm.cdelaof26.pmmicro.exception.FieldAlreadyTaken;
import es.upm.cdelaof26.pmmicro.exception.ProviderNotFound;
import es.upm.cdelaof26.pmmicro.repository.ProviderRepository;
import java.util.List;
import es.upm.cdelaof26.pmmicro.model.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Provider service
 * @author cristopher
 */
@Service
public class ProviderService {
    private final Logger l = LoggerFactory.getLogger(ProviderService.class);
    
    private final ProviderRepository repository;

    public ProviderService(ProviderRepository repository) {
        this.repository = repository;
    }
    
    public boolean existsById(Integer providerId) {
        return repository.existsById(providerId);
    }
    
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
    
    public boolean existsByPhone(String phone) {
        return repository.existsByPhone(phone);
    }
    
    
    public List<Provider> getAllProviders() {
        return repository.findAll();
    }
    
    public Provider getProvider(Integer providerId) {
        if (!existsById(providerId)) {
            l.error(String.format("Providers with id %d doesn't exist", providerId));
            throw new ProviderNotFound(providerId);
        }
        
        return repository.findById(providerId).get();
    }
    
    public Provider save(Provider p) {
        if (existsByEmail(p.getEmail())) {
            l.error(String.format("Email '%s' is already taken", p.getEmail()));
            throw new FieldAlreadyTaken("correo", p.getEmail());
        }
        
        if (existsByPhone(p.getPhone())) {
            l.error(String.format("Phone '%s' is already taken", p.getPhone()));
            throw new FieldAlreadyTaken("número telefónico", p.getPhone());
        }
        
        return repository.save(p);
    }
}
