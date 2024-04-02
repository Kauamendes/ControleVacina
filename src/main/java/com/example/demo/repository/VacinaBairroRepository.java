package com.example.demo.repository;

import java.sql.*;
import java.util.List;

import com.example.demo.dto.VacinaBairroDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.VacinaBairro;

@Repository
public interface VacinaBairroRepository extends JpaRepository<VacinaBairro, Long>  {

    @Query("SELECT NEW com.seupacote.dto.VacinaBairroDto(v.nome, b.nome, COUNT(vb)) " +
            "FROM VacinaBairro vb " +
            "JOIN vb.bairro b " +
            "JOIN vb.vacina v " +
            "WHERE (:bairroId IS NULL OR b.id = :bairroId) " +
            "AND (:vacinaId IS NULL OR v.id = :vacinaId) " +
            "AND (:dataInicio IS NULL OR vb.dataAplicacao >= :dataInicio) " +
            "AND (:dataFim IS NULL OR vb.dataAplicacao <= :dataFim) " +
            "GROUP BY v.nome, b.nome " +
            "ORDER BY v.nome")
    List<VacinaBairroDto> buscar(
            @Param("bairroId") Long bairroId,
            @Param("vacinaId") Long vacinaId,
            @Param("dataInicio") Timestamp dataInicio,
            @Param("dataFim") Timestamp dataFim);

}
