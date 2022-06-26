package com.example.safira.controllers;

import com.example.safira.models.Certificate;
import com.example.safira.models.Employee;
import com.example.safira.models.Service;
import com.example.safira.services.CertificateService;
import com.example.safira.services.ServiceService;
import com.example.safira.services.UserService;
import com.example.safira.utils.Util;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/certificate")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class CertificateController {
    private CertificateService certificateService;
    private ServiceService serviceService;
    private UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateController.class);

    @GetMapping
    public ResponseEntity<List<Certificate>> getAll() {
        LOGGER.info("Received all certificates");

        return ResponseEntity.ok().body(certificateService.findAll());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Certificate> get(@PathVariable long id) {
        Certificate certificate = Util.getRecordFromTable(id, certificateService);
        LOGGER.info("Received certificate with id = " + certificate.getId());

        return ResponseEntity.ok().body(certificate);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Certificate> add(@RequestBody Certificate certificate) {
        BigDecimal price = certificate.getPrice();
        Set<Service> services = new HashSet<>();
        Certificate certificateForCreate = null;

        if (!certificate.getServices().isEmpty()) {
            for (Service service : certificate.getServices()) {
                Service serviceFromDb = serviceService.findAll()
                        .stream()
                        .filter(s -> s.getName().equals(service.getName()))
                        .findFirst()
                        .orElse(null);

                if (serviceFromDb != null) {
                    services.add(serviceFromDb);
                    price = price.add(service.getPrice());
                }
            }

            certificate.setFullNameRecipient(certificate.getFullNameRecipient());
            certificate.setPhoneNumber(certificate.getPhoneNumber());
            certificate.setUser(certificate.getUser());
            certificate.setServices(services);
            certificate.setPrice(price);
            certificateForCreate = certificateService.save(certificate);
        }

        return ResponseEntity.status(201).body(certificateForCreate);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Certificate> delete(@PathVariable long id) {
        Certificate deleteCertificate = Util.getRecordFromTable(id, certificateService);
        certificateService.delete(deleteCertificate);
        LOGGER.info(String.format("DELETED CERTIFICATE WITH ID  = %d", deleteCertificate.getId()));

        return ResponseEntity.ok().body(deleteCertificate);
    }
}
