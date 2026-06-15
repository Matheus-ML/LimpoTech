package com.example.LimpezaWMS.services;
import com.example.LimpezaWMS.dtos.ProdutoDto;
import com.example.LimpezaWMS.models.ProdutoModel;
import com.example.LimpezaWMS.repositories.ProdutoRepository;
import com.example.LimpezaWMS.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    //Criar
    public Boolean criarProduto (ProdutoDto dados){
        ProdutoModel produtoModel = new ProdutoModel();

        produtoModel.setNomeProduto(dados.getNomeProduto());
        produtoModel.setQuantidadeProduto(dados.getQuantidadeProduto());

        return true;
    }

    //Update
    public Boolean editarProduto(ProdutoDto dados){
        Optional<ProdutoModel> produtoOp = produtoRepository.findById(dados.getId());
        if (produtoOp.isEmpty()){
            return false;
        }

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNomeProduto(dados.getNomeProduto());
        produtoModel.setQuantidadeProduto(dados.getQuantidadeProduto());

        produtoRepository.save(produtoModel);

        return true;
    }

    //Read
    public List<ProdutoDto> listandoProduto (){
    List<ProdutoDto> listaProdutoDto = new ArrayList<>();
    List<ProdutoModel> listaProdutoModel = produtoRepository.findAll();

    for (ProdutoModel produtoModel : listaProdutoModel){
        ProdutoDto produtoDto = new ProdutoDto();

        produtoDto.setNomeProduto(produtoModel.getNomeProduto());
        produtoDto.setQuantidadeProduto(produtoModel.getQuantidadeProduto());

        listaProdutoDto.add(produtoDto);
    }
    return  listaProdutoDto;
    }


    //Delete
    boolean excluirProduto (Long id){
        Optional<ProdutoModel> produtoOP = produtoRepository.findById(id);

        if (produtoOP.isEmpty()){
           return false;
        }else{
            produtoRepository.delete(produtoOP.get());
            return true;
        }
    }
}
