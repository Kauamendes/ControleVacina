package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.Bairro;
import com.example.demo.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Long> {

    Bairro findByNome(String nome);

}
