package com.example.LimpezaWMS.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String paginaLogin(
            @RequestParam(value = "erro",  required = false) String erro,
            @RequestParam(value = "saiu",  required = false) String saiu,
            Model model
    ) {
        if (erro != null)  model.addAttribute("mensagem", "E-mail ou senha inválidos.");
        if (saiu != null)  model.addAttribute("mensagem", "Você saiu do sistema.");
        return "login"; // templates/login.html
    }
}
