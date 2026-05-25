package com.example.LimpezaWMS.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class Usuario {
    private Long id;
    private String email;
    private String senha;
    private String role;
}
