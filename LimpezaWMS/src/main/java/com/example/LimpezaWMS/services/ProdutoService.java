package com.example.LimpezaWMS.services;

import com.example.LimpezaWMS.dtos.ProdutoDto;
import com.example.LimpezaWMS.models.ProdutoModel;
import com.example.LimpezaWMS.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Page<ProdutoModel> listar(String nomeProduto, Pageable pageable) {
        if (nomeProduto != null && !nomeProduto.isBlank()) {
            return produtoRepository.findByNomeProdutoContainingIgnoreCase(nomeProduto, pageable);
        }
        return produtoRepository.findAll(pageable);
    }

    public ProdutoModel buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
    }

    public ProdutoModel criar(ProdutoDto dto) {
        ProdutoModel produto = new ProdutoModel();
        produto.setNomeProduto(dto.getNomeProduto());
        produto.setQuantidadeProduto(dto.getQuantidadeProduto());
        return produtoRepository.save(produto);
    }

    public ProdutoModel editar(Long id, ProdutoDto dto) {
        ProdutoModel produto = buscarPorId(id);
        produto.setNomeProduto(dto.getNomeProduto());
        produto.setQuantidadeProduto(dto.getQuantidadeProduto());
        return produtoRepository.save(produto);
    }

    public void excluir(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
