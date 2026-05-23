package es.upm.cdelaof26.pmicro.controller;

import es.upm.cdelaof26.pmicro.dto.PaymentDto;
import es.upm.cdelaof26.pmicro.service.PaymentService;
import es.upm.cdelaof26.pmicro.model.Payment;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Payment REST controller
 * @author cristopher
 */
@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final Logger l = LoggerFactory.getLogger(PaymentController.class);
    
    private final WebMvcLinkBuilder self = WebMvcLinkBuilder.linkTo(PaymentController.class);
    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(service.getAllPayments());
    }
    
    @PostMapping
    public ResponseEntity<Void> createPayment(@RequestBody @Valid PaymentDto p) {
        l.debug("Creating a new payment entry...");
        
        Payment _p = service.save(p);
        
        l.debug("Payment entry created successfully");
        return ResponseEntity.created(self.slash(_p.getId()).toUri()).build();
    }
    
    @PutMapping("/{paymentId}")
    public ResponseEntity<Void> updatePayment(
        @PathVariable int paymentId, @RequestBody @Valid PaymentDto p
    ) {
        l.debug("Performing update to payment...");
        service.validateParameterCombination(p);
        Payment _p = service.performingPayment(paymentId, p);
        if (_p.getPaymentMethod() == null) {
            // Payment is not being performed
            l.debug("Payment infomation updated");
            return ResponseEntity.noContent().build();
        }
        
        service.performPayment(_p, p.getVehicleId(), p.getStartDate(), p.getEndDate());
        return ResponseEntity.accepted().build();
    }
}
