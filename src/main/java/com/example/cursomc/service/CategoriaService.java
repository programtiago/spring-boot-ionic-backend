package com.example.cursomc.service;

import com.example.cursomc.entity.Categoria;
import com.example.cursomc.repository.CategoriaRepository;
import com.example.cursomc.service.exceptions.DataIntegrityException;
import com.example.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria procurar(Integer id)
    {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria inserir(Categoria obj)
    {
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria atualizar(Categoria obj)
    {
        procurar(obj.getId());
        return repo.save(obj);
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
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }
    }
}
