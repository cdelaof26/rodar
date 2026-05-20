package es.upm.cdelaof26.tmicro.controller;

import es.upm.cdelaof26.tmicro.service.TaxesService;
import es.upm.cdelaof26.tmicro.dto.PriceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Taxes REST controller
 * @author cristopher
 */
@RestController
@RequestMapping
public class TaxesController {
    private final Logger l = LoggerFactory.getLogger(TaxesController.class);
    
    private final TaxesService service;

    public TaxesController(TaxesService service) {
        this.service = service;
    }
    
    @GetMapping("/currencies/{c}/rates")
    public ResponseEntity<PriceDto> getPricingPerDay(
        @PathVariable String c, @RequestParam String to, @RequestParam float amount,
        @RequestParam(required = false) boolean addTaxes
    ) {
        l.debug(String.format("Convert %f %s to %s...", amount, c, to));
        
        return ResponseEntity.ok(service.convertCurrency(c, to, amount, addTaxes));
    }
}
