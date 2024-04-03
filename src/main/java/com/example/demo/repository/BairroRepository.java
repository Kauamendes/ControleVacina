package com.example.demo.repository;

import com.example.demo.domain.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Long> {

    Bairro findByNome(String nome);

}
