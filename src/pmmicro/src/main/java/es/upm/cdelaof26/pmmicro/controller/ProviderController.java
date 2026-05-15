package es.upm.cdelaof26.pmmicro.controller;

import es.upm.cdelaof26.pmmicro.service.ProviderService;
import java.util.List;
import es.upm.cdelaof26.pmmicro.model.Provider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provider REST controller
 * @author cristopher
 */
@RestController
@RequestMapping("/providers")
public class ProviderController {
    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }
    
    @GetMapping
    public ResponseEntity<List<Provider>> getAllProviders() {
        return ResponseEntity.ok(providerService.getAllProviders());
    }
}
