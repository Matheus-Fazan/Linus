package com.linus.dao;

import com.linus.dto.BoletimDto;
import com.linus.dto.GeraBoletimDto;
import com.linus.dto.NotaDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.exception.dao.NoRegistersAlteredException;
import com.linus.infra.connection.ConnectionManager;
import com.linus.model.dao.Nota;
import com.linus.utils.DaoUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDao implements GenericDaoInterface<Nota, NotaDto> {

    private final String SQL_SAVE_COMMAND = "INSERT INTO nota(n1, n2, media, id_professor, id_aluno) VALUES(?, ?, ?, ?, ?) RETURNING id";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM nota WHERE id = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM nota";
    private final String SQL_UPDATE_COMMAND = "UPDATE nota SET n1 = ?, n2 = ?, media = ?, id_professor = ?, id_aluno = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM nota WHERE id = ?";
    private final String SQL_FIND_BY_MATRICULA = """
            SELECT
                a.nome                                              AS nome_aluno,
                COALESCE(t.nome, 'Sem turma')                      AS turma,
                m.nome                                              AS materia,
                n.n1,
                n.n2,
                n.media,
                case
                    WHEN (n.media) IS NULL  THEN 'Em processo'
                    WHEN n.media >= 7 THEN 'Aprovado'
                    ELSE                   'Reprovado'
                END                                                 AS situacao,
                COALESCE(n.observacao, '-')                         AS observacao
            FROM nota n
            JOIN aluno    a ON a.matricula  = n.id_aluno
            JOIN professor p ON p.id        = n.id_professor
            JOIN materia  m ON m.id         = p.id_materia
            LEFT JOIN turma t ON t.id       = a.id_turma
            WHERE n.id_aluno = ?
            ORDER BY m.nome;
            """;

    @Override
    public Nota save(NotaDto dto) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setBigDecimal(1, new BigDecimal(dto.n1));
            ps.setBigDecimal(2, new BigDecimal(dto.n2));
            ps.setBigDecimal(3, new BigDecimal(dto.media));
            ps.setLong(4, Long.parseLong(dto.idProfessor));
            ps.setLong(5, Long.parseLong(dto.idAluno));

            queryResult = ps.executeQuery();

            Nota nota = null;
            if (queryResult.next()) {
                nota = new Nota(queryResult);
            }

            return nota;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Nota findById(NotaDto dto) throws SQLException, ConnectionException {
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, Long.parseLong(dto.id));

            queryResult = ps.executeQuery();

            Nota nota = null;
            if (queryResult.next()) {
                nota = new Nota(queryResult);
            }

            return nota;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public List<Nota> findAll() throws SQLException, ConnectionException {
        List<Nota> notas = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDALL_COMMAND);

            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                notas.add(new Nota(queryResult));
            }

            return notas;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public void update(NotaDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setBigDecimal(1, new BigDecimal(dto.n1));
            ps.setBigDecimal(2, new BigDecimal(dto.n2));
            ps.setBigDecimal(3, new BigDecimal(dto.media));
            ps.setLong(4, Long.parseLong(dto.idProfessor));
            ps.setLong(5, Long.parseLong(dto.idAluno));
            ps.setLong(6, Long.parseLong(dto.id));

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    @Override
    public void delete(NotaDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
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

    public List<GeraBoletimDto> findByMatricula(int matricula) throws ConnectionException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<GeraBoletimDto> boletim = new ArrayList<>();

        try (Connection conn = ConnectionManager.connect()) {
            ps = conn.prepareStatement(SQL_FIND_BY_MATRICULA);
            ps.setLong(1, matricula);

            rs = ps.executeQuery();

            while (rs.next()) {
                boletim.add(new GeraBoletimDto(rs));
            }

            return boletim;

        } finally {
            DaoUtil.closeResources(ps, rs);
        }
    }
}