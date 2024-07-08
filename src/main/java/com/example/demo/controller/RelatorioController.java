package com.example.demo.controller;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.Usuario;
import com.example.demo.domain.VacinaBairro;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.DosagemEnum;
import com.example.demo.repository.BairroRepository;
import com.example.demo.repository.RelatorioRepository;
import com.example.demo.repository.VacinaRepository;
import com.example.demo.services.VacinaBairroService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private VacinaBairroService vacinaBairroService;

    @GetMapping
    public ModelAndView relatorio(HttpSession session, HttpServletResponse response) throws IOException, SQLException {
        ModelAndView mv = new ModelAndView("relatorio");
        String cargo = (String) session.getAttribute(NomeVariaveisSessao.CARGO);
        if (cargo == null) {
            response.sendRedirect("/");
        } else if (cargo.equals(Usuario.TIP_CARGO_APLICADOR)) {
            response.sendRedirect("/vacinas");
        }
        mv.addObject(NomeVariaveisSessao.CARGO, cargo);
        mv.addObject("bairros", vacinaBairroService.listarBairros());
        mv.addObject("vacinas", vacinaBairroService.listarVacinas());
        mv.addObject("dosagens",  Arrays.stream(DosagemEnum.values()).toList());
        return mv;
    }

    @PostMapping("/buscar")
    public ModelAndView buscar(RelatorioDto relatorioDto, HttpSession session) throws SQLException {
        ModelAndView mv = new ModelAndView("relatorio");

        String cargo = (String) session.getAttribute(NomeVariaveisSessao.CARGO);
        if (Objects.nonNull(cargo)) mv.addObject(NomeVariaveisSessao.CARGO, cargo);

        List<VacinaBairroDto> vacinas = relatorioRepository.buscar(relatorioDto);
        mv.addObject("unicaDose", vacinas.stream().filter(VacinaBairroDto::isUnica).toList());
        mv.addObject("primeiraDose", vacinas.stream().filter(VacinaBairroDto::isPrimeiraDose).toList());
        mv.addObject("segundaDose", vacinas.stream().filter(VacinaBairroDto::isSegundaDose).toList());
        mv.addObject("terceiraDose", vacinas.stream().filter(VacinaBairroDto::isTerceiraDose).toList());
        mv.addObject("reforcoDose", vacinas.stream().filter(VacinaBairroDto::isReforco).toList());

        mv.addObject("bairros", vacinaBairroService.listarBairros());
        mv.addObject("vacinas", vacinaBairroService.listarVacinas());
        mv.addObject("dosagens",  Arrays.stream(DosagemEnum.values()).toList());

        if (!relatorioDto.getBairro().isBlank())
            mv.addObject(NomeVariaveisSessao.BAIRRO, Long.parseLong(relatorioDto.getBairro()));
        if (!relatorioDto.getVacina().isBlank())
            mv.addObject(NomeVariaveisSessao.VACINA, Long.parseLong(relatorioDto.getVacina()));
        if (!relatorioDto.getDosagem().isBlank())
            mv.addObject(NomeVariaveisSessao.DOSAGEM, Long.parseLong(relatorioDto.getDosagem()));
        if (!relatorioDto.getDataInicio().isBlank())
            mv.addObject(NomeVariaveisSessao.DATA_INICIO, LocalDateTime.parse(relatorioDto.getDataInicio()));
        if (!relatorioDto.getDataFim().isBlank())
            mv.addObject(NomeVariaveisSessao.DATA_FIM, LocalDateTime.parse(relatorioDto.getDataFim()));
        return mv;
    }

    @GetMapping("/listar")
    public ModelAndView listar(HttpSession session, HttpServletResponse response) throws IOException, SQLException {
        ModelAndView mv = new ModelAndView("listagemVacina");
        String cargo = (String) session.getAttribute(NomeVariaveisSessao.CARGO);

        if (cargo == null) {
            response.sendRedirect("/");
        } else if (cargo.equals(Usuario.TIP_CARGO_APLICADOR)) {
            response.sendRedirect("/vacinas");
        }
        mv.addObject(NomeVariaveisSessao.CARGO, cargo);
        mv.addObject("bairros", vacinaBairroService.listarBairros());
        mv.addObject("vacinas", vacinaBairroService.listarVacinas());
        mv.addObject("dosagens",  Arrays.stream(DosagemEnum.values()).toList());
        return mv;
    }

    @PostMapping("/listar")
    public ModelAndView listar(RelatorioDto relatorioDto, HttpSession session) throws SQLException {
        ModelAndView mv = new ModelAndView("listagemVacina");
        mv.addObject("bairros", vacinaBairroService.listarBairros());
        mv.addObject("vacinas", vacinaBairroService.listarVacinas());

        List<VacinaBairroDto> vacinas = relatorioRepository.listar(relatorioDto);
        mv.addObject("unicaDose", vacinas.stream().filter(VacinaBairroDto::isUnica).toList());
        mv.addObject("primeiraDose", vacinas.stream().filter(VacinaBairroDto::isPrimeiraDose).toList());
        mv.addObject("segundaDose", vacinas.stream().filter(VacinaBairroDto::isSegundaDose).toList());
        mv.addObject("terceiraDose", vacinas.stream().filter(VacinaBairroDto::isTerceiraDose).toList());
        mv.addObject("reforcoDose", vacinas.stream().filter(VacinaBairroDto::isReforco).toList());

        mv.addObject("dosagens",  Arrays.stream(DosagemEnum.values()).toList());

        String cargo = (String) session.getAttribute(NomeVariaveisSessao.CARGO);
        if (Objects.nonNull(cargo)) mv.addObject(NomeVariaveisSessao.CARGO, cargo);

        if (!relatorioDto.getBairro().isBlank())
            mv.addObject(NomeVariaveisSessao.BAIRRO, Long.parseLong(relatorioDto.getBairro()));
        if (!relatorioDto.getVacina().isBlank())
            mv.addObject(NomeVariaveisSessao.VACINA, Long.parseLong(relatorioDto.getVacina()));
        if (!relatorioDto.getDataInicio().isBlank())
            mv.addObject(NomeVariaveisSessao.DATA_INICIO, LocalDateTime.parse(relatorioDto.getDataInicio()));
        if (!relatorioDto.getDataFim().isBlank())
            mv.addObject(NomeVariaveisSessao.DATA_FIM, LocalDateTime.parse(relatorioDto.getDataFim()));
        if (!relatorioDto.getDosagem().isBlank())
            mv.addObject(NomeVariaveisSessao.DOSAGEM, Long.parseLong(relatorioDto.getDosagem()));

        return mv;
    }

    @PostMapping("/exportar/quantitativo")
    public void exportarDadosQuantitativo(HttpServletResponse response, RelatorioDto dto) throws IOException, SQLException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio.xlsx");


        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório de Vacinas");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Bairro");
        headerRow.createCell(1).setCellValue("Vacina");
        headerRow.createCell(2).setCellValue("Quantidade");

        List<VacinaBairroDto> relatorios = relatorioRepository.buscar(dto);

        int rowIdx = 1;
        for (VacinaBairroDto relatorio : relatorios) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(relatorio.getBairro());
            row.createCell(1).setCellValue(relatorio.getVacina());
            row.createCell(2).setCellValue(relatorio.getQuantidade());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @PostMapping("/exportar/descritivo")
    public void exportarDadosDescritivo(HttpServletResponse response, RelatorioDto dto) throws IOException, SQLException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio.xlsx");


        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório de Vacinas");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Bairro");
        headerRow.createCell(1).setCellValue("Vacina");
        headerRow.createCell(2).setCellValue("Dose");
        headerRow.createCell(3).setCellValue("Aplicador");
        headerRow.createCell(4).setCellValue("Data aplicada");
        headerRow.createCell(5).setCellValue("Observações");

        List<VacinaBairroDto> relatorios = relatorioRepository.listar(dto);

        int rowIdx = 1;
        for (VacinaBairroDto relatorio : relatorios) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(relatorio.getBairro());
            row.createCell(1).setCellValue(relatorio.getVacina());
            row.createCell(2).setCellValue(relatorio.getDose());
            row.createCell(3).setCellValue(relatorio.getAplicador());
            row.createCell(4).setCellValue(relatorio.getDataAplicacao());
            row.createCell(5).setCellValue(relatorio.getObservacoes());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
