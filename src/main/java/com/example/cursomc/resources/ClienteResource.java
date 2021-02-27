package com.example.cursomc.resources;

import com.example.cursomc.dto.CategoriaDTO;
import com.example.cursomc.dto.ClienteDTO;
import com.example.cursomc.dto.ClienteDTO;
import com.example.cursomc.dto.ClienteNewDTO;
import com.example.cursomc.entity.Categoria;
import com.example.cursomc.entity.Cliente;
import com.example.cursomc.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> encontrar(@PathVariable Integer id)
    {
        Cliente obj = service.procurar(id);
        return ResponseEntity.ok().body(obj);
    }


    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Void> inserir(@Valid @RequestBody ClienteNewDTO objDto)
    {
        Cliente obj = service.fromDTO(objDto);
        obj = service.inserir(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizar(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){

        Cliente obj = service.fromDTO(objDto);
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
    public ResponseEntity <List<ClienteDTO>> listarTodas()
    {
        List<Cliente> list = service.listarTodas();
        List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);

    }

    @RequestMapping(value = "/pagina", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> encontrarPagina(@RequestParam(value="pagina", defaultValue = "0") Integer pagina,
                                                              @RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
                                                              @RequestParam(value="ordernarPor", defaultValue = "nome") String ordernarPor,
                                                              @RequestParam(value="direcao", defaultValue = "ASC") String direcao)
    {
        Page<Cliente> list = service.encontrarPagina(pagina, linhasPorPagina, ordernarPor, direcao);
        Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));

        return ResponseEntity.ok().body(listDto);
    }

}
