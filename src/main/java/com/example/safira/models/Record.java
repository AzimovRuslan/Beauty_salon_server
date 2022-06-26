package com.example.safira.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @NotEmpty
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;

    @OneToOne
    @NotEmpty
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Service service;

    @NotEmpty
    private BigDecimal price;

    @NotEmpty(message = "Date cannot be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotEmpty(message = "Start time cannot be empty")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time startTime;

    @NotEmpty(message = "Finish time cannot be empty")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time finishTime;

    @OneToOne
    @NotEmpty(message = "Employee cannot be empty")
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
}
