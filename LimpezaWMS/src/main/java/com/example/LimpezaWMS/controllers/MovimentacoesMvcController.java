package com.example.LimpezaWMS.controllers;

import com.example.LimpezaWMS.models.MovimentacoesModel;
import com.example.LimpezaWMS.models.ProdutoModel;
import com.example.LimpezaWMS.repositories.MovimentacoesRepository;
import com.example.LimpezaWMS.repositories.ProdutoRepository;
import com.example.LimpezaWMS.services.MovimentacoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movimentacoes")
@RequiredArgsConstructor
public class MovimentacoesMvcController {

    private final MovimentacoesService movimentacoesService;
    private final MovimentacoesRepository movimentacoesRepository;
    private final ProdutoRepository produtoRepository;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim,
            @RequestParam(required = false) String tipo,
            Model model
    ) {
        List<MovimentacoesModel> movimentacoes = movimentacoesRepository
                .findAllByOrderByDataEhoraDesc();

        // Filtro por tipo
        if (tipo != null && !tipo.isBlank()) {
            movimentacoes = movimentacoes.stream()
                    .filter(m -> m.getTipo().equalsIgnoreCase(tipo))
                    .collect(Collectors.toList());
        }

        // Filtro por data início
        if (dataInicio != null && !dataInicio.isBlank()) {
            LocalDateTime inicio = LocalDate.parse(dataInicio).atStartOfDay();
            movimentacoes = movimentacoes.stream()
                    .filter(m -> !m.getDataEhora().isBefore(inicio))
                    .collect(Collectors.toList());
        }

        // Filtro por data fim
        if (dataFim != null && !dataFim.isBlank()) {
            LocalDateTime fim = LocalDate.parse(dataFim).atTime(23, 59, 59);
            movimentacoes = movimentacoes.stream()
                    .filter(m -> !m.getDataEhora().isAfter(fim))
                    .collect(Collectors.toList());
        }

        List<ProdutoModel> produtos = produtoRepository.findAll();

        model.addAttribute("movimentacoes", movimentacoes);
        model.addAttribute("produtos", produtos);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);
        model.addAttribute("tipo", tipo);

        return "movimentacoes";
    }

    @PostMapping
    public String registrar(
            @RequestParam Long produtoId,
            @RequestParam String tipo,
            @RequestParam Integer quantidade,
            @AuthenticationPrincipal UserDetails usuarioLogado,
            RedirectAttributes redirect
    ) {
        try {
            // Monta o DTO manualmente a partir dos parâmetros do formulário
            com.example.LimpezaWMS.dtos.MovimentacoesDto dto = new com.example.LimpezaWMS.dtos.MovimentacoesDto();
            com.example.LimpezaWMS.dtos.ProdutoDto produtoDto = new com.example.LimpezaWMS.dtos.ProdutoDto();
            produtoDto.setId(produtoId);
            dto.setProduto(produtoDto);
            dto.setTipo(tipo);
            dto.setQuantidade(quantidade);

            movimentacoesService.registrar(dto, usuarioLogado.getUsername());
            redirect.addFlashAttribute("sucesso", "Movimentação registrada com sucesso!");
        } catch (IllegalStateException e) {
            // Estoque insuficiente — reabre o modal com a mensagem de erro
            redirect.addAttribute("erro", e.getMessage());
        } catch (Exception e) {
            redirect.addAttribute("erro", e.getMessage());
        }
        return "redirect:/movimentacoes";
    }
}
