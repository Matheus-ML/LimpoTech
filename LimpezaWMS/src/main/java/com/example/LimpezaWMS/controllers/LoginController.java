package com.example.LimpezaWMS.controllers;

import com.example.LimpezaWMS.dtos.UsuarioDto;
import com.example.LimpezaWMS.models.UsuarioModel;
import com.example.LimpezaWMS.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String viewLogin(Model model){
        UsuarioDto usuarioDto = new UsuarioDto();

        model.addAttribute("usuarioDto",usuarioDto);

        return "login";
    }

    @PostMapping("/login")
    public String autenticar(UsuarioDto usuarioDto){
        Boolean valida = usuarioService.validaUsuario(usuarioDto);
        if (valida = false){

        }

    }


}
