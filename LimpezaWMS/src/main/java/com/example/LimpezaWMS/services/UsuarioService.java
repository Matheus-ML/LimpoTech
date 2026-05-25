package com.example.LimpezaWMS.services;

import com.example.LimpezaWMS.dtos.UsuarioDto;
import com.example.LimpezaWMS.models.UsuarioModel;
import com.example.LimpezaWMS.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Criar
    public Boolean UsuarioCriar(UsuarioDto dados){
        if (dados.getEmail().isEmpty() || dados.getSenha().isEmpty() || dados.getRole().isEmpty()){
            System.out.println("Email, Senha ou Nivel de Acesso está vázio.");
            return false;
        }

        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setEmail(dados.getEmail());
        usuarioModel.setSenha(dados.getSenha());
        usuarioModel.setRole(dados.getRole());
        usuarioRepository.save(usuarioModel);

        return true;
    }

    //Editar
    public Boolean UsuarioCriar(UsuarioDto dados){
        Optional<UsuarioModel> usuarioOp = usuarioRepository.findById(dados.getId());
        if (usuarioOp.isEmpty()){
            return false;
        }


    }
    //Listar
    //Deletar

}
