package com.example.LimpezaWMS.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDto {
    private String email;
    private String senha;
    private String role;
}
