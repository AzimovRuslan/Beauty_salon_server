package com.example.safira.models;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Name may not be null")
    private String name;
    private String surname;
    private String patronymic;
    private int phoneNumber;
    private BigDecimal salary;

    @OneToOne
    @NotNull(message = "Service may not be null")
    @JoinColumn(name = "service_type_id", referencedColumnName = "id")
    private ServiceType serviceType;
}
