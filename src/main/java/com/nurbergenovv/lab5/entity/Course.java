package com.nurbergenovv.lab5.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity()
@Table(name = "courses")

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int price;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<ApplicationRequest> applicationRequests;
}
