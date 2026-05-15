package es.upm.cdelaof26.pmmicro.service;

import es.upm.cdelaof26.pmmicro.repository.ProviderRepository;
import java.util.List;
import es.upm.cdelaof26.pmmicro.model.Provider;
import org.springframework.stereotype.Service;

/**
 * Provider service
 * @author cristopher
 */
@Service
public class ProviderService {
    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }
    
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }
}
