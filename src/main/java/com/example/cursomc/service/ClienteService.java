package com.example.cursomc.service;

import com.example.cursomc.dto.ClienteDTO;
import com.example.cursomc.dto.ClienteNewDTO;
import com.example.cursomc.entity.*;
import com.example.cursomc.enums.TipoCliente;
import com.example.cursomc.repository.ClienteRepository;
import com.example.cursomc.repository.EnderecoRepository;
import com.example.cursomc.service.exceptions.DataIntegrityException;
import com.example.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente procurar(Integer id)
    {
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente inserir(Cliente obj)
    {
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
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

    public Cliente fromDTO(ClienteNewDTO objDto)
    {
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());

        if (objDto.getTelefone2() != null)
        {
            cli.getTelefones().add(objDto.getTelefone2());
        }

        if (objDto.getTelefone3() != null)
        {
            cli.getTelefones().add(objDto.getTelefone3());
        }

        return cli;
    }

    private void atualizarDados(Cliente newObj, Cliente obj)
    {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

    /* A listagem de produtos que contém o texto do nome dado e que pertencem a pelo menos umas das categorias dadas
    public Page<Produto> pesquisar(String nome, List<Integer> ids)
    {

    }

     */







}
