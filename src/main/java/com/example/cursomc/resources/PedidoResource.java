package com.example.cursomc.resources;

import com.example.cursomc.entity.Pedido;
import com.example.cursomc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pedido> encontrar(@PathVariable Integer id)
    {
        Pedido obj = service.procurar(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Void> inserir(@Valid @RequestBody Pedido obj)
    {
        obj = service.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
