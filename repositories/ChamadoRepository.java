package com.example.demo.repositories;

import com.example.demo.domain.entities.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Integer> { // O tipo Integer em JpaRepository<Chamado, Integer> se refere ao tipo de dado da chave primária/id do Chamado
    // Herda operações prontas de CRUD (findAll, findById, save, deleteById, etc.)
    // Não precisa declarar nada aqui enquanto não houver consultas personalizadas
}


