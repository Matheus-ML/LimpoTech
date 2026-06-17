package com.example.LimpezaWMS.controllers;

import com.example.LimpezaWMS.dtos.MovimentacoesDto;
import com.example.LimpezaWMS.models.MovimentacoesModel;
import com.example.LimpezaWMS.services.MovimentacoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")
@RequiredArgsConstructor
public class MovimentacoesController {

    private final MovimentacoesService movimentacoesService;

    // Registra uma nova movimentação — pega o usuário logado automaticamente
    @PostMapping
    public ResponseEntity<?> registrar(
            @RequestBody MovimentacoesDto dto,
            @AuthenticationPrincipal UserDetails usuarioLogado
    ) {
        try {
            MovimentacoesModel movimentacao = movimentacoesService.registrar(dto, usuarioLogado.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(movimentacao);
        } catch (IllegalStateException e) {
            // Estoque insuficiente
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<MovimentacoesModel>> listarTodas() {
        return ResponseEntity.ok(movimentacoesService.listarTodas());
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<MovimentacoesModel>> listarPorProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(movimentacoesService.listarPorProduto(produtoId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MovimentacoesModel>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(movimentacoesService.listarPorUsuario(usuarioId));
    }
}
