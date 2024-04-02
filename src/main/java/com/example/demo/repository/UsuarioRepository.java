package com.example.demo.repository;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.LoginDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLoginAndSenha(String login, String senha);

    Usuario findByLogin(String login);
}
