package es.upm.cdelaof26.pmicro.service;

import es.upm.cdelaof26.pmicro.dto.PaymentDto;
import es.upm.cdelaof26.pmicro.dto.VehicleUpdateDto;
import es.upm.cdelaof26.pmicro.exception.FieldAlreadyTakenException;
import es.upm.cdelaof26.pmicro.exception.InvalidDateException;
import es.upm.cdelaof26.pmicro.exception.MissingVehicleIdException;
import es.upm.cdelaof26.pmicro.exception.PaymentMethodUnsupportedException;
import es.upm.cdelaof26.pmicro.exception.PaymentNotFoundException;
import es.upm.cdelaof26.pmicro.mapper.PaymentMapper;
import es.upm.cdelaof26.pmicro.model.Payment;
import es.upm.cdelaof26.pmicro.repository.PaymentMethodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import es.upm.cdelaof26.pmicro.repository.PaymentRepository;
import es.upm.cdelaof26.pmicro.repository.StatusRepository;
import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Payment service
 * @author cristopher
 */
@Service
public class PaymentService {
    private final Logger l = LoggerFactory.getLogger(PaymentService.class);
    
    private final PaymentRepository paymentRepository;
    private final StatusRepository statusRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private static final PaymentMapper mapper = new PaymentMapper();
    
    private final RestTemplate restTemplate;
    private final String VIMICRO_URL;

    public PaymentService(
        Environment environment, RestTemplate restTemplate, 
        PaymentRepository paymentRepository, StatusRepository statusRepository, 
        PaymentMethodRepository paymentMethodRepository
    ) {
        this.VIMICRO_URL = environment.getProperty("vimicro.base-url");
        this.restTemplate = restTemplate;
        
        this.paymentRepository = paymentRepository;
        this.statusRepository = statusRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }
    
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    
    public Payment save(PaymentDto p) {
        if (paymentRepository.existsByReservationId(p.getReservationId())) {
            l.error(String.format("ReservationId '%d' is already taken", p.getReservationId()));
            throw new FieldAlreadyTakenException("identificador de reservación", p.getReservationId().toString());
        }
        
        if (p.getPaymentMethod() != null)
            l.warn("Ignoring pay method when creating entry");
        
        Payment _p = mapper.toPayment(p);
        _p.setId(null);
        _p.setStatus(statusRepository.findByName("pending_payment").get());
        
        return paymentRepository.save(_p);
    }
    
    // TODO: Move restTemplate calls to another class
    
    private String updateVehicleAvailability(int vehicleId, String startDate, String endDate, Payment p) {
        URI uri = UriComponentsBuilder.fromUriString(VIMICRO_URL + "/vehicles")
            .path("/{vehicleId}")
            .buildAndExpand(Map.of(
                "vehicleId", vehicleId
            ))
            .toUri();
        
        VehicleUpdateDto b = new VehicleUpdateDto(
            startDate, endDate, p.getUserId(), 
            LocalDate.now().isAfter(Date.valueOf(startDate).toLocalDate())
        );
        
        ResponseEntity<String> resp = restTemplate.exchange(
            uri,
            HttpMethod.PUT,
            new HttpEntity<>(b, HttpHeaders.EMPTY),
            String.class
        );
        
        return resp.getBody();
    }
    
    @Async("threadPoolTaskExecutor")
    public void performPayment(Payment p, int vehicleId, String startDate, String endDate) {
        l.info("Performing payment...");
        try { Thread.sleep((long) (Math.random() * 15000)); } catch (InterruptedException ex) { }
        p.setStatus(statusRepository.findByName("confirmed").get());
        paymentRepository.save(p);
        
        l.info("Payment has been confirmed...");
        l.info("Placing notification for the user in nmicro...");
        
        try { Thread.sleep((long) (Math.random() * 10000)); } catch (InterruptedException ex) { }
        p.setStatus(statusRepository.findByName("completed").get());
        paymentRepository.save(p);
        
        l.info("Payment has been completed!");
        l.info("Placing notification for the user in nmicro...");
        
        // TODO: Proper error handling
        l.info(String.format("Updating availability for vehicleId=%d...", vehicleId));
        String licensePlate = updateVehicleAvailability(vehicleId, startDate, endDate, p);
        l.info(String.format("Notifying user of their plates: %s", licensePlate));
        // TODO: Probably is better to have the plates in the reservation
    }
    
    private void validateDate(String d, String name) {
        if (!d.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            l.error(String.format("Invalid value '%s' for %s", d, name));
            throw new InvalidDateException(d);
        }
    }
    
    public void validateParameterCombination(PaymentDto p) {
        // This assumes that both dates must be present with a payment method
        // and a vehicle id for this to work. But it's totally posible that 
        // someone might want to update any of these parameters...
        //
        if (p.getStartDate() != null || p.getEndDate() != null) {
            validateDate(p.getStartDate(), "startDate");
            validateDate(p.getEndDate(), "endDate");
            
            if (p.getPaymentMethod() == null) {
                l.error("Date ranges were given but no payment method");
                throw new PaymentMethodUnsupportedException();
            }
            
            if (p.getVehicleId() == null) {
                l.error("Date ranges were given but no vehicleId");
                throw new MissingVehicleIdException();
            }
        }
    }
    
    public Payment performingPayment(int paymentId, PaymentDto p) {
        if (!paymentRepository.existsById(paymentId)) {
            l.error(String.format("Payment not found by id %d", paymentId));
            throw new PaymentNotFoundException(paymentId);
        }
        
        Payment _p = mapper.toPayment(p);
        if (p.getPaymentMethod() != null) {
            if (!paymentMethodRepository.existsByName(p.getPaymentMethod())) {
                l.error(String.format("Unknown payment method '%s'", p.getPaymentMethod()));
                throw new PaymentMethodUnsupportedException(p.getPaymentMethod());
            }
            
            _p.setPaymentMethod(paymentMethodRepository.findByName(p.getPaymentMethod()).get());
        }
        
        _p.setStatus(paymentRepository.findById(paymentId).get().getStatus());
        _p.setId(paymentId);
        
        return paymentRepository.save(_p);
    }
}
