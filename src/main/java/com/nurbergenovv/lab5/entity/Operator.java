package com.nurbergenovv.lab5.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "operators")
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String department;

    @ManyToMany(mappedBy = "operators", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ApplicationRequest> requests = new ArrayList<>();
}