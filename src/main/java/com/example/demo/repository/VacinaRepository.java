package com.example.demo.repository;

import com.example.demo.domain.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long>  {

}
