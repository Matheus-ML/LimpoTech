package com.example.LimpezaWMS.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Produto")
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeProduto;
    private Integer quantidadeProduto;

}
