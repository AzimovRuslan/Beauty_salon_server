package com.example.safira;

import com.example.safira.models.Certificate;
import com.example.safira.models.Service;
import com.example.safira.services.CertificateService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class SafiraApplicationTests {
    @Autowired
    private CertificateService certificateService;

    @Test
    void contextLoads() {
        LocalDate date = LocalDate.now();
        System.out.println(date);
    }

}
