package com.example.cursomc.repository;

import com.example.cursomc.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository <Pagamento, Integer> {
}
