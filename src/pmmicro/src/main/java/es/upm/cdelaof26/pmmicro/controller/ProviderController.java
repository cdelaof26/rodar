package es.upm.cdelaof26.pmmicro.controller;

import es.upm.cdelaof26.pmmicro.service.ProviderService;
import java.util.List;
import es.upm.cdelaof26.pmmicro.model.Provider;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provider REST controller
 * @author cristopher
 */
@RestController
@RequestMapping("/providers")
public class ProviderController {
    private final Logger l = LoggerFactory.getLogger(ProviderController.class);
    
    private final WebMvcLinkBuilder self = WebMvcLinkBuilder.linkTo(ProviderController.class);
    private final ProviderService service;

    public ProviderController(ProviderService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<Provider>> getAllProviders() {
        l.debug("Listing all providers...");
        return ResponseEntity.ok(service.getAllProviders());
    }
    
    @GetMapping("/{providerId}")
    public ResponseEntity<Provider> getProvider(@PathVariable int providerId) {
        l.debug(String.format("Listing provider with id %d...", providerId));
        
        Provider p = service.getProvider(providerId);
        p.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ProviderController.class).getProvider(providerId)
        ).withSelfRel());
        
        return ResponseEntity.ok(p);
    }
    
    @PostMapping
    public ResponseEntity<Void> createProvider(@RequestBody @Valid Provider p) {
        l.debug("Create a new provider. Running validations...");
        
        Provider _p = service.save(p);
        
        l.debug("Provider created successfully");
        return ResponseEntity.created(self.slash(_p.getId()).toUri()).build();
    }
}
