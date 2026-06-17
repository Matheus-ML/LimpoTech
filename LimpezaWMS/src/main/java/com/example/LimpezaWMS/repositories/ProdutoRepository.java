package com.example.LimpezaWMS.repositories;

import com.example.LimpezaWMS.models.ProdutoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel,Long> {
    // Filtro por nome com paginação (case insensitive)
    Page<ProdutoModel> findByNomeProdutoContainingIgnoreCase(String nomeProduto, Pageable pageable);
}
