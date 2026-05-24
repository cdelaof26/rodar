package es.upm.cdelaof26.remicro.controller;

import es.upm.cdelaof26.remicro.dto.ReservationDto;
import es.upm.cdelaof26.remicro.service.ReservationService;
import es.upm.cdelaof26.remicro.model.Reservation;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Reservation REST controller
 * @author cristopher
 */
@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final Logger l = LoggerFactory.getLogger(ReservationController.class);
    
    private final WebMvcLinkBuilder self = WebMvcLinkBuilder.linkTo(ReservationController.class);
    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(service.getAllReservations());
    }
    
    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody @Valid ReservationDto p) {
        l.debug("Creating a new reservation entry...");
        
        Reservation _p = service.save(p);
        
        l.debug("Reservation entry created successfully");
        return ResponseEntity.created(self.slash(_p.getId()).toUri()).build();
    }
}
