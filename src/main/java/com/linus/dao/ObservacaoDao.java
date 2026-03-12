package com.linus.dao;

import com.linus.dto.ObservacaoDto;
import com.linus.dto.ObservacaoProfessorDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.exception.dao.NoRegistersAlteredException;
import com.linus.infra.connection.ConnectionManager;
import com.linus.model.dao.Observacao;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObservacaoDao implements GenericDaoInterface<Observacao, ObservacaoDto> {

    private final String SQL_SAVE_COMMAND = """
    INSERT INTO observacao (observacao, data_criacao, id_professor, id_aluno) VALUES (?, NOW(), ?, ?)
    """;
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM observacao WHERE id = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM observacao";
    private final String SQL_FINDALL_BY_ID_COMMAND =
            "SELECT \n" +
            "    p.nome AS nome_professor,\n" +
            "    m.nome AS materia_professor,\n" +
            "    o.observacao,\n" +
            "    TO_CHAR(o.data_criacao, 'DD/MM/YYYY') AS data_publicacao\n" +
            "FROM observacao o\n" +
            "JOIN professor p ON o.id_professor = p.id\n" +
            "JOIN materia m ON p.id_materia = m.id\n" +
            "WHERE o.id_aluno = ?";
    private final String SQL_UPDATE_COMMAND = "UPDATE observacao SET observacao = ?, id_professor = ?, id_aluno = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM observacao WHERE id = ?";
    private final String SQL_FIND_BY_PROFESSOR = """
        SELECT
            o.id,
            a.nome          AS nome_aluno,
            m.nome          AS disciplina,
            p.nome          AS nome_professor,
            o.observacao,
            TO_CHAR(o.data_criacao, 'DD/MM/YYYY HH24:MI') AS data_criacao
        FROM observacao o
        JOIN aluno     a ON a.matricula = o.id_aluno
        JOIN professor p ON p.id        = o.id_professor
        JOIN materia   m ON m.id        = p.id_materia
        WHERE o.id_professor = ?
        ORDER BY o.data_criacao DESC
       """;

    @Override
    public Observacao save(ObservacaoDto dto) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, dto.observacao);
            ps.setLong(2, Long.parseLong(dto.idProfessor));
            ps.setLong(3, Long.parseLong(dto.idAluno));

            queryResult = ps.executeQuery();

            Observacao observacao = null;
            if (queryResult.next()) {
                observacao = new Observacao(queryResult);
            }

            return observacao;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    public void save(long idProfessor, long matricula, String observacao) throws SQLException, ConnectionException {
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);
            ps.setString(1, observacao);
            ps.setLong(2, idProfessor);
            ps.setLong(3, matricula);
            ps.executeUpdate();

        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    @Override
    public Observacao findById(ObservacaoDto dto) throws SQLException, ConnectionException {
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, Long.parseLong(dto.id));

            queryResult = ps.executeQuery();

            Observacao observacao = null;
            if (queryResult.next()) {
                observacao = new Observacao(queryResult);
            }

            return observacao;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public List<Observacao> findAll() throws SQLException, ConnectionException {
        List<Observacao> observacoes = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDALL_COMMAND);

            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                observacoes.add(new Observacao(queryResult));
            }

            return observacoes;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    public List<ObservacaoDto> findAllById(Long id) throws SQLException, ConnectionException {
        List<ObservacaoDto> observacoes = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDALL_BY_ID_COMMAND);

            ps.setLong(1, id);
            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                observacoes.add(new ObservacaoDto(queryResult));
            }

            return observacoes;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public void update(ObservacaoDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, dto.observacao);
            ps.setLong(2, Long.parseLong(dto.idProfessor));
            ps.setLong(3, Long.parseLong(dto.idAluno));
            ps.setLong(4, Long.parseLong(dto.id));

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    @Override
    public void delete(ObservacaoDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_DELETE_COMMAND);

            ps.setLong(1, Long.parseLong(dto.id));

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    public List<ObservacaoProfessorDto> findByProfessor(long idProfessor) throws ConnectionException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ObservacaoProfessorDto> resultado = new ArrayList<>();

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FIND_BY_PROFESSOR);
            ps.setLong(1, idProfessor);

            rs = ps.executeQuery();

            while (rs.next()) {
                resultado.add(new ObservacaoProfessorDto(rs));
            }

            return resultado;

        } finally {
            DaoUtil.closeResources(ps, rs);
        }
    }
}