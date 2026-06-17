package com.example.LimpezaWMS.services;

import com.example.LimpezaWMS.dtos.MovimentacoesDto;
import com.example.LimpezaWMS.models.MovimentacoesModel;
import com.example.LimpezaWMS.models.ProdutoModel;
import com.example.LimpezaWMS.models.UsuarioModel;
import com.example.LimpezaWMS.repositories.MovimentacoesRepository;
import com.example.LimpezaWMS.repositories.ProdutoRepository;
import com.example.LimpezaWMS.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacoesService {

    private final MovimentacoesRepository movimentacoesRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public MovimentacoesModel registrar(MovimentacoesDto dto, String emailUsuario) {

        // Busca o produto e o usuário responsável
        ProdutoModel produto = produtoRepository.findById(dto.getProduto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        UsuarioModel usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        String tipo = dto.getTipo().toUpperCase();

        // Regra de negócio: bloqueia saída se estoque insuficiente
        if (tipo.equals("SAIDA")) {
            if (produto.getQuantidadeProduto() < dto.getQuantidade()) {
                throw new IllegalStateException(
                    "Estoque insuficiente. Disponível: " + produto.getQuantidadeProduto()
                );
            }
            produto.setQuantidadeProduto(produto.getQuantidadeProduto() - dto.getQuantidade());
        } else if (tipo.equals("ENTRADA")) {
            produto.setQuantidadeProduto(produto.getQuantidadeProduto() + dto.getQuantidade());
        } else {
            throw new IllegalArgumentException("Tipo inválido. Use ENTRADA ou SAIDA.");
        }

        // Atualiza o estoque do produto
        produtoRepository.save(produto);

        // Registra a movimentação
        MovimentacoesModel movimentacao = new MovimentacoesModel();
        movimentacao.setTipo(tipo);
        movimentacao.setQuantidade(dto.getQuantidade());
        movimentacao.setDataEhora(LocalDateTime.now());
        movimentacao.setProduto(produto);
        movimentacao.setUsuario(usuario);

        return movimentacoesRepository.save(movimentacao);
    }

    public List<MovimentacoesModel> listarTodas() {
        return movimentacoesRepository.findAll();
    }

    public List<MovimentacoesModel> listarPorProduto(Long produtoId) {
        return movimentacoesRepository.findByProdutoIdOrderByDataEhoraDesc(produtoId);
    }

    public List<MovimentacoesModel> listarPorUsuario(Long usuarioId) {
        return movimentacoesRepository.findByUsuarioIdOrderByDataEhoraDesc(usuarioId);
    }
}
