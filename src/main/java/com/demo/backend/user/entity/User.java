package com.demo.backend.user.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(
    name = "user",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone")

    }

) //定义表名
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
}
