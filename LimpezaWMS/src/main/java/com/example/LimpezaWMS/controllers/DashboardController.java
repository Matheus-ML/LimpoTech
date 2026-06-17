package com.example.LimpezaWMS.controllers;

import com.example.LimpezaWMS.models.MovimentacoesModel;
import com.example.LimpezaWMS.repositories.MovimentacoesRepository;
import com.example.LimpezaWMS.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ProdutoRepository produtoRepository;
    private final MovimentacoesRepository movimentacoesRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        // Total de produtos cadastrados
        long totalProdutos = produtoRepository.count();

        // Soma de todas as unidades em estoque
        int totalEstoque = produtoRepository.findAll()
                .stream()
                .mapToInt(p -> p.getQuantidadeProduto())
                .sum();

        // Movimentações de hoje
        LocalDate hoje = LocalDate.now();
        List<MovimentacoesModel> todasMovimentacoes = movimentacoesRepository.findAll();

        long movimentacoesHoje = todasMovimentacoes.stream()
                .filter(m -> m.getDataEhora().toLocalDate().equals(hoje))
                .count();

        // Últimas 10 movimentações para a tabela
        List<MovimentacoesModel> ultimasMovimentacoes = movimentacoesRepository
                .findTop10ByOrderByDataEhoraDesc();

        model.addAttribute("totalProdutos", totalProdutos);
        model.addAttribute("totalEstoque", totalEstoque);
        model.addAttribute("movimentacoesHoje", movimentacoesHoje);
        model.addAttribute("ultimasMovimentacoes", ultimasMovimentacoes);

        return "dashboard";
    }
}
