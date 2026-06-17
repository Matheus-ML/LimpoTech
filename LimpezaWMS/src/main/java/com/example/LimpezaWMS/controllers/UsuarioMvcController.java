package com.example.LimpezaWMS.controllers;

import com.example.LimpezaWMS.dtos.UsuarioDto;
import com.example.LimpezaWMS.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioMvcController {

    private final UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios";
    }

    @PostMapping
    public String criar(@ModelAttribute UsuarioDto dto, RedirectAttributes redirect) {
        try {
            usuarioService.criar(dto);
            redirect.addFlashAttribute("sucesso", "Usuário criado com sucesso!");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @PutMapping("/{id}")
    public String editar(
            @PathVariable Long id,
            @ModelAttribute UsuarioDto dto,
            RedirectAttributes redirect
    ) {
        try {
            usuarioService.editar(id, dto);
            redirect.addFlashAttribute("sucesso", "Usuário atualizado com sucesso!");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @DeleteMapping("/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            usuarioService.excluir(id);
            redirect.addFlashAttribute("sucesso", "Usuário excluído com sucesso!");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/usuarios";
    }
}
