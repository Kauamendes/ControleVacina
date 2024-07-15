package com.example.demo.repository;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.demo.domain.Bairro;
import com.example.demo.domain.Vacina;
import com.example.demo.dto.VacinaBairroDto;
import com.example.demo.enums.DosagemEnum;
import com.example.demo.utils.DateUtils;
import org.springframework.stereotype.Repository;

import com.example.demo.config.Conexao;
import com.example.demo.domain.VacinaBairro;

@Repository
public class VacinaBairroRepository {

    public void insert(VacinaBairro vacinaBairro) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        try {
            String query = "INSERT INTO VACINA_BAIRRO (VACINA_ID, BAIRRO_ID, DOSE, APLICADOR, OBSERVACOES)" +
                    " VALUES(?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, vacinaBairro.getVacinaId());
            ps.setLong(2, vacinaBairro.getBairroId());
            ps.setString(3, vacinaBairro.getDose());
            ps.setString(4, vacinaBairro.getAplicador());
            ps.setString(5, vacinaBairro.getObservacoes());
            ps.execute();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            conexao.desconectar(conn);
        }
    }

    public List<Bairro> listarBairros() throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Bairro> bairros = new ArrayList<>();
        String sql = "SELECT * FROM BAIRRO";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(sql);

            while (resultado.next()) {
                Bairro bairro = Bairro.builder().build();
                bairro.setId(resultado.getLong("id"));
                bairro.setNome(resultado.getString("nome"));
                bairros.add(bairro);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar bairros: " + e.getMessage());
        } finally {
            conexao.desconectar(conn);
        }
        return bairros;
    }

    public List<Vacina> listarVacinas() throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<Vacina> vacinas = new ArrayList<>();
        String sql = "SELECT * FROM VACINA";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(sql);

            while (resultado.next()) {
                Vacina vacina = Vacina.builder().build();
                vacina.setId(resultado.getLong("id"));
                vacina.setNome(resultado.getString("nome"));
                vacina.setDosagem(resultado.getBoolean("dosagem"));
                vacinas.add(vacina);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar vacinas: " + e.getMessage());
        } finally {
            conexao.desconectar(conn);
        }
        return vacinas;
    }

    public Bairro buscarBairroPorNome(String nomeBairro) throws SQLException {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, nome FROM BAIRRO WHERE NOME LIKE ? ");
        preparedStatement.setString(1, nomeBairro);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Bairro bairro = new Bairro();
            bairro.setId(resultSet.getLong("id"));
            bairro.setNome(resultSet.getString("nome"));
            return bairro;
        }
        return null;
    }

    public List<VacinaBairroDto> listarUltimosPorUsuario(String usuarioLogado) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        List<VacinaBairroDto> vacinas = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT vb.id as id, v.NOME AS vacina, b.NOME AS bairro, vb.aplicador as aplicador, vb.dose as dose, vb.data_aplicacao as data, vb.observacoes as obs FROM VACINA_BAIRRO vb ");
        query.append(" INNER JOIN BAIRRO b ON b.id = vb.BAIRRO_ID ");
        query.append(" INNER JOIN VACINA v ON v.id = vb.VACINA_ID ");
        query.append(" WHERE vb.aplicador=LOWER(?) ");
        query.append(" ORDER BY vb.data_aplicacao DESC LIMIT 30");

        try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            stmt.setObject(1, usuarioLogado);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                vacinas.add(VacinaBairroDto.builder()
                        .id(resultSet.getLong("id"))
                        .vacina(resultSet.getString("vacina"))
                        .bairro(resultSet.getString("bairro"))
                        .dose(getDose(resultSet.getString("dose")))
                        .aplicador(resultSet.getString("aplicador"))
                        .dataAplicacao(DateUtils.parseTimestampToString(resultSet.getTimestamp("data")))
                        .observacoes(resultSet.getString("obs"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conexao.desconectar(conn);
        }

        return vacinas;
    }

    private String getDose(String dose) {
        return Objects.nonNull(dose) && !dose.isEmpty() ? DosagemEnum.findByValor(Integer.parseInt(dose)) : "";
    }

    public VacinaBairroDto buscarVacinaBairroPorId(Long id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        VacinaBairroDto vacina = null;

        StringBuilder query = new StringBuilder("SELECT vb.id as id, vb.vacina_id AS vacina, vb.bairro_id AS bairro FROM VACINA_BAIRRO vb ");
        query.append(" WHERE vb.id=? ");

        try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            stmt.setObject(1, id);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                vacina = VacinaBairroDto.builder()
                        .id(resultSet.getLong("id"))
                        .vacina(resultSet.getString("vacina"))
                        .bairro(resultSet.getString("bairro"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conexao.desconectar(conn);
        }

        return vacina;
    }

    public void update(VacinaBairro vacinaBairro) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        try {
            String query = "UPDATE VACINA_BAIRRO " +
                    "SET VACINA_ID=?, BAIRRO_ID=?, DOSE=?, APLICADOR=?, MODIFIED_DATE=?, OBSERVACOES=? WHERE ID=?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, vacinaBairro.getVacinaId());
            ps.setLong(2, vacinaBairro.getBairroId());
            ps.setString(3, vacinaBairro.getDose());
            ps.setString(4, vacinaBairro.getAplicador());
            ps.setTimestamp(5, Timestamp.from(Instant.now()));
            ps.setString(6, vacinaBairro.getObservacoes());
            ps.setLong(7, vacinaBairro.getId());
            ps.execute();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            conexao.desconectar(conn);
        }
    }

    public void excluirPorId(Long id) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();

        String query = "DELETE FROM VACINA_BAIRRO WHERE ID=?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conexao.desconectar(conn);
        }
    }
}
