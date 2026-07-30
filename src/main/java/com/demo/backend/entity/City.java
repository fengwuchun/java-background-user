package com.demo.backend.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class City {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String population;

     

    
}
