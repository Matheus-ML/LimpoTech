package com.example.LimpezaWMS.repositories;

import com.example.LimpezaWMS.models.MovimentacoesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacoesRepository extends JpaRepository<MovimentacoesModel, Long> {

    // Listagem geral ordenada por data (mais recente primeiro)
    List<MovimentacoesModel> findAllByOrderByDataEhoraDesc();

    // Últimas 10 para o dashboard
    List<MovimentacoesModel> findTop10ByOrderByDataEhoraDesc();

    // Histórico por produto
    List<MovimentacoesModel> findByProdutoIdOrderByDataEhoraDesc(Long produtoId);

    // Histórico por usuário
    List<MovimentacoesModel> findByUsuarioIdOrderByDataEhoraDesc(Long usuarioId);
}
