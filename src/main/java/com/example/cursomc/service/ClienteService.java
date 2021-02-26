package com.example.cursomc.service;

import com.example.cursomc.dto.ClienteDTO;
import com.example.cursomc.entity.Cliente;
import com.example.cursomc.repository.ClienteRepository;
import com.example.cursomc.service.exceptions.DataIntegrityException;
import com.example.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Cliente procurar(Integer id)
    {
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente atualizar(Cliente obj)
    {
        Cliente newObj = procurar(obj.getId());
        atualizarDados(newObj, obj);
        procurar(obj.getId());
        return repo.save(newObj);
    }

    public void eliminar(Integer id)
    {
        procurar(id);
        try
        {
            repo.deleteById(id);
        }
        catch(DataIntegrityViolationException e)
        {
            throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
        }
    }

    public List<Cliente> listarTodas()
    {
        return repo.findAll();
    }

    public Page<Cliente> encontrarPagina(Integer pagina, Integer linhasPorPagina, String ordenarPor, String direcao)
    {
        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), ordenarPor);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDto)
    {
       return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    private void atualizarDados(Cliente newObj, Cliente obj)
    {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }


}
