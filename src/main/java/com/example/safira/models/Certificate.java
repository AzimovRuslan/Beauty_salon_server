package com.example.safira.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Phone number cannot be empty")
    private String phoneNumber;

    @NotEmpty(message = "Services cannot be empty")
    private String fullNameRecipient;

    @OneToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "certificate_service",
            joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    @NotEmpty(message = "Services cannot be empty")
    private Set<Service> services = new HashSet<>();

    private BigDecimal price = BigDecimal.valueOf(0.00);
}
