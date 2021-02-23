package com.example.cursomc.resources;

import com.example.cursomc.entity.Categoria;
import com.example.cursomc.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> encontrar(@PathVariable Integer id)
    {
        Categoria obj = service.procurar(id);
        return ResponseEntity.ok().body(obj);
    }
}
