package com.example.cursomc.resources;

import com.example.cursomc.dto.CategoriaDTO;
import com.example.cursomc.entity.Categoria;
import com.example.cursomc.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<Categoria> encontrar(@PathVariable Integer id)
    {
        Categoria obj = service.procurar(id);
        return ResponseEntity.ok().body(obj);

    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizar(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){

        Categoria obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.atualizar(obj);

        return ResponseEntity.noContent().build();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> eliminar(@PathVariable Integer id)
    {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity <List<CategoriaDTO>> listarTodas()
    {
        List<Categoria> list = service.listarTodas();
        List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);

    }

    @RequestMapping(value = "/pagina", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> encontrarPagina(@RequestParam(value="pagina", defaultValue = "0") Integer pagina,
                                                              @RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
                                                              @RequestParam(value="ordernarPor", defaultValue = "nome") String ordernarPor,
                                                              @RequestParam(value="direcao", defaultValue = "ASC") String direcao)
    {
        Page<Categoria> list = service.encontrarPagina(pagina, linhasPorPagina, ordernarPor, direcao);
        Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));

        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Void> inserir(@Valid @RequestBody CategoriaDTO objDto)
    {
        Categoria obj = service.fromDTO(objDto);
        obj = service.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }




}
