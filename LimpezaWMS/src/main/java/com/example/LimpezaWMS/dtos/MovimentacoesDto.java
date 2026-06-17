package com.example.LimpezaWMS.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class MovimentacoesDto {
    private Long id;
    private LocalDateTime dataEhora;
    private UsuarioDto usuario;
    private ProdutoDto produto;

    private String tipo;
    private Integer quantidade;

}
