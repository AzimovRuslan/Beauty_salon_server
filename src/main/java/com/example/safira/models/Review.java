package com.example.safira.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    private LocalDate date = LocalDate.now();

    @ManyToOne
    private Employee employee;
}
