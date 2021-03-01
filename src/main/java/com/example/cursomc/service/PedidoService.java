package com.example.cursomc.service;

import com.example.cursomc.entity.Cliente;
import com.example.cursomc.entity.Pedido;
import com.example.cursomc.repository.ClienteRepository;
import com.example.cursomc.repository.PedidoRepository;
import com.example.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public Pedido procurar(Integer id)
    {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    /* Um novo pedido a ser inserido na base de dados
    public Pedido inserir(Pedido obj)
    {

    }

     */
}
