package com.example.demo.repository;

import com.example.demo.domain.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Long> {

    Optional<Bairro> findByNome(String nome);

}
