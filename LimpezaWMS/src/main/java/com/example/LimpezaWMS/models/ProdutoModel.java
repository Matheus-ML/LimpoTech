package com.example.LimpezaWMS.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Produto")
public class ProdutoModel {

    private String nomeProduto;
    private Integer quantidadeProduto;

}
