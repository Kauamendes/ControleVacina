package com.example.demo.domain;

import java.sql.Timestamp;

import com.example.demo.enums.DosagemEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class VacinaBairro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vacina vacina;

    @ManyToOne
    private Bairro bairro;

    private Timestamp dataAplicacao;

    @Enumerated(EnumType.STRING)
    private DosagemEnum dose;

    private String aplicador;

    private String observacoes;
}
