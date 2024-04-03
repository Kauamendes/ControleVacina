package com.example.demo.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.example.demo.enums.CargoEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String senha;

    @Enumerated(EnumType.STRING)
    private CargoEnum cargo;

    public boolean isAplicador() {
        return Objects.equals(CargoEnum.APLICADOR, cargo);
    }

    public boolean isGestor() {
        return Objects.equals(CargoEnum.GESTOR, cargo);
    }

    public boolean isAdmin() {
        return Objects.equals(CargoEnum.ADMIN, cargo);
    }

    public static List<CargoEnum> getAllCargos() {
        return Arrays.stream(CargoEnum.values()).toList();
    }
}
