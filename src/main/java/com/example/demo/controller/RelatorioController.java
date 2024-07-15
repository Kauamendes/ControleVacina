package com.example.demo.controller;

import com.example.demo.NomeVariaveisSessao;
import com.example.demo.domain.Usuario;
import com.example.demo.dto.RelatorioDto;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.DosagemEnum;
import com.example.demo.repository.RelatorioRepository;
import com.example.demo.services.VacinaBairroService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

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

        CellStyle headerCellStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerCellStyle.setFont(headerFont);

        // Cabeçalho
        Row headerRow = sheet.createRow(0);
        Cell headerCell0 = headerRow.createCell(0);
        headerCell0.setCellValue("Bairro");
        headerCell0.setCellStyle(headerCellStyle);

        Cell headerCell1 = headerRow.createCell(1);
        headerCell1.setCellValue("Vacina");
        headerCell1.setCellStyle(headerCellStyle);

        Cell headerCell2 = headerRow.createCell(2);
        headerCell2.setCellValue("Quantidade");
        headerCell2.setCellStyle(headerCellStyle);

        List<VacinaBairroDto> relatorios = relatorioRepository.buscar(dto);

        AtomicInteger rowIdx = new AtomicInteger(1);

        CellStyle sectionTitleStyle = workbook.createCellStyle();
        Font sectionTitleFont = workbook.createFont();
        sectionTitleFont.setBold(true);
        sectionTitleStyle.setFont(sectionTitleFont);

        BiConsumer<String, List<VacinaBairroDto>> createSection = (sectionTitle, filteredReports) -> {
            Row sectionRow = sheet.createRow(rowIdx.getAndIncrement());
            Cell sectionTitleCell = sectionRow.createCell(0);
            sectionTitleCell.setCellValue(sectionTitle);
            sectionTitleCell.setCellStyle(sectionTitleStyle);

            for (VacinaBairroDto relatorio : filteredReports) {
                Row row = sheet.createRow(rowIdx.getAndIncrement());
                row.createCell(0).setCellValue(relatorio.getBairro());
                row.createCell(1).setCellValue(relatorio.getVacina());
                row.createCell(2).setCellValue(relatorio.getQuantidade());
            }
            rowIdx.getAndIncrement();
        };

        createSection.accept("Dose Única", relatorios.stream().filter(VacinaBairroDto::isUnica).toList());
        createSection.accept("Primeira Dose", relatorios.stream().filter(VacinaBairroDto::isPrimeiraDose).toList());
        createSection.accept("Segunda Dose", relatorios.stream().filter(VacinaBairroDto::isSegundaDose).toList());
        createSection.accept("Terceira Dose", relatorios.stream().filter(VacinaBairroDto::isTerceiraDose).toList());
        createSection.accept("Reforço", relatorios.stream().filter(VacinaBairroDto::isReforco).toList());

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @PostMapping("/exportar/descritivo")
    public void exportarDadosDescritivo(HttpServletResponse response, RelatorioDto dto) throws IOException, SQLException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório de Vacinas");

        CellStyle headerCellStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Bairro", "Vacina", "Dose", "Aplicador", "Data aplicada", "Observações"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
        }

        List<VacinaBairroDto> relatorios = relatorioRepository.listar(dto);

        AtomicInteger rowIdx = new AtomicInteger(1);

        CellStyle sectionTitleStyle = workbook.createCellStyle();
        Font sectionTitleFont = workbook.createFont();
        sectionTitleFont.setBold(true);
        sectionTitleStyle.setFont(sectionTitleFont);

        BiConsumer<String, List<VacinaBairroDto>> createSection = (sectionTitle, filteredReports) -> {
            Row sectionRow = sheet.createRow(rowIdx.getAndIncrement());
            Cell sectionTitleCell = sectionRow.createCell(0);
            sectionTitleCell.setCellValue(sectionTitle);
            sectionTitleCell.setCellStyle(sectionTitleStyle);

            for (VacinaBairroDto relatorio : filteredReports) {
                Row row = sheet.createRow(rowIdx.getAndIncrement());
                row.createCell(0).setCellValue(relatorio.getBairro());
                row.createCell(1).setCellValue(relatorio.getVacina());
                row.createCell(2).setCellValue(relatorio.getDose());
                row.createCell(3).setCellValue(relatorio.getAplicador());
                row.createCell(4).setCellValue(relatorio.getDataAplicacao());
                row.createCell(5).setCellValue(relatorio.getObservacoes());
            }
            rowIdx.getAndIncrement();
        };

        createSection.accept("Dose Única", relatorios.stream().filter(VacinaBairroDto::isUnica).toList());
        createSection.accept("Primeira Dose", relatorios.stream().filter(VacinaBairroDto::isPrimeiraDose).toList());
        createSection.accept("Segunda Dose", relatorios.stream().filter(VacinaBairroDto::isSegundaDose).toList());
        createSection.accept("Terceira Dose", relatorios.stream().filter(VacinaBairroDto::isTerceiraDose).toList());
        createSection.accept("Reforço", relatorios.stream().filter(VacinaBairroDto::isReforco).toList());

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
