package com.example.safira.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.*;

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
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @OneToOne
    @NotEmpty(message = "Service type cannot be empty")
    @JoinColumn(name = "service_type_id", referencedColumnName = "id")
    private ServiceType serviceType;

    @NotEmpty(message = "Price cannot be empty")
    private BigDecimal price;
}
