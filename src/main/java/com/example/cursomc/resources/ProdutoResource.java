package com.example.cursomc.resources;

import com.example.cursomc.dto.CategoriaDTO;
import com.example.cursomc.dto.ProdutoDTO;
import com.example.cursomc.entity.Categoria;
import com.example.cursomc.entity.Cliente;
import com.example.cursomc.entity.Produto;
import com.example.cursomc.resources.utils.URL;
import com.example.cursomc.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> encontrar(@PathVariable Integer id)
    {
        Produto obj = service.encontrar(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> encontrarPagina(
            @RequestParam(value="nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value= "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
            @RequestParam(value="ordernarPor", defaultValue = "nome") String ordernarPor,
            @RequestParam(value="direcao", defaultValue = "ASC") String direcao)
    {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> list = service.pesquisar(nomeDecoded, ids, pagina, linhasPorPagina, ordernarPor, direcao);
        Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));

        return ResponseEntity.ok().body(listDto);
    }
}
