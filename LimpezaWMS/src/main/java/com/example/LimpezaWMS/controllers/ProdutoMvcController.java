package com.example.LimpezaWMS.controllers;

import com.example.LimpezaWMS.dtos.ProdutoDto;
import com.example.LimpezaWMS.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoMvcController {

    private final ProdutoService produtoService;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nomeProduto").ascending());
        model.addAttribute("produtos", produtoService.listar(nome, pageable));
        model.addAttribute("filtroNome", nome);
        return "produtos";
    }

    @PostMapping
    public String criar(@ModelAttribute ProdutoDto dto, RedirectAttributes redirect) {
        try {
            produtoService.criar(dto);
            redirect.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/produtos";
    }

    @PutMapping("/{id}")
    public String editar(
            @PathVariable Long id,
            @ModelAttribute ProdutoDto dto,
            RedirectAttributes redirect
    ) {
        try {
            produtoService.editar(id, dto);
            redirect.addFlashAttribute("sucesso", "Produto atualizado com sucesso!");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/produtos";
    }

    @DeleteMapping("/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            produtoService.excluir(id);
            redirect.addFlashAttribute("sucesso", "Produto excluído com sucesso!");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/produtos";
    }
}
