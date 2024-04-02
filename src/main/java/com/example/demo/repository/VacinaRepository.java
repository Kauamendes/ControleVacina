package com.example.demo.repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.Usuario;
import com.example.demo.domain.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long>  {

}
