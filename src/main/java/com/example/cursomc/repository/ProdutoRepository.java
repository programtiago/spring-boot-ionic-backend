package com.example.cursomc.repository;

import com.example.cursomc.entity.Categoria;
import com.example.cursomc.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Integer> {
}
