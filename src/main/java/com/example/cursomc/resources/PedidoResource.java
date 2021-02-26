package com.example.cursomc.resources;

import com.example.cursomc.entity.Cliente;
import com.example.cursomc.entity.Pedido;
import com.example.cursomc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
