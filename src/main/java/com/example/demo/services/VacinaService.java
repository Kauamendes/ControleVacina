package com.example.demo.services;

import com.example.demo.domain.Usuario;
import com.example.demo.domain.Vacina;
import com.example.demo.dto.VacinaDto;
import com.example.demo.repository.VacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacinaService {
    @Autowired
    private VacinaRepository vacinaRepository;
    @Autowired
    private Usuario usuario;
    public void insert(VacinaDto vacinaDto) throws Exception {
        if (usuario.getCargo().equals(usuario.TIP_CARGO_APLICADOR)) {
            Vacina vacina = Vacina.builder()
                    .id(Long.valueOf(vacinaDto.getId()))
                    .nome(vacinaDto.getNome())
                    .build();
            vacinaRepository.insert(vacina);
        } else {
            throw new Exception("");
        }
    }

    public void findVacinaById(Long id){

    }
}
