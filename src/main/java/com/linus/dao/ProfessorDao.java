package com.linus.dao;

import com.linus.dto.AlunosProfessorDto;
import com.linus.dto.ProfessorDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.exception.dao.NoRegistersAlteredException;
import com.linus.infra.connection.ConnectionManager;
import com.linus.model.dao.Professor;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDao implements GenericDaoInterface<Professor, ProfessorDto> {

    // Ajustado para usar a coluna 'usuario' em vez de 'cpf'
    private final String SQL_SAVE_COMMAND = "INSERT INTO professor(nome, email, hash_senha, usuario, id_materia) VALUES(?, ?, ?, ?, ?) RETURNING id";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM professor WHERE id = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM professor";
    private static final String SQL_FIND_ALUNOS_BY_PROFESSOR = """
            SELECT
                a.matricula,
                a.nome AS nome_aluno,
                COALESCE(t.nome, 'Sem turma') AS turma,
                COALESCE(n.n1::VARCHAR, '-') AS n1,
                COALESCE(n.n2::VARCHAR, '-') AS n2
            FROM professor p
            JOIN aluno
                ON a.id_turma IS NOT NULL
            LEFT JOIN turma 
                ON a.id_turma = t.id
            LEFT JOIN nota 
                ON n.id_aluno = a.matricula
                AND n.id_professor = p.id
            WHERE p.id = ?                         
            ORDER BY t.nome, a.nome;
            """;
    private final String SQL_UPDATE_COMMAND = "UPDATE professor SET nome = ?, email = ?, hash_senha = ?, usuario = ?, id_materia = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM professor WHERE id = ?";
    private static final String SQL_UPDATE_PASSWORD = """
        UPDATE professor SET hash_senha = ? WHERE id = ?
        """;

    @Override
    public Professor save(ProfessorDto dto) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, dto.nome);
            ps.setString(2, dto.email);
            ps.setString(3, DaoUtil.toBCryptHash(dto.senha));
            ps.setString(4, dto.usuario);
            ps.setLong(5, Long.parseLong(dto.idMateria));

            queryResult = ps.executeQuery();

            Professor professor = null;
            if (queryResult.next()) {
                professor = new Professor(queryResult);
            }

            return professor;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    public String saveReturningId(ProfessorDto dto) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, dto.nome);
            ps.setString(2, dto.email);
            ps.setString(3, DaoUtil.toBCryptHash(dto.senha));
            ps.setString(4, dto.usuario);
            ps.setLong(5, Long.parseLong(dto.idMateria));

            queryResult = ps.executeQuery();

            String generatedKey = null;

            if (queryResult.next()) {
                generatedKey = queryResult.getString("id");
            }

            return generatedKey;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Professor findById(ProfessorDto dto) throws SQLException, ConnectionException {
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, Long.parseLong(dto.id));

            queryResult = ps.executeQuery();

            Professor professor = null;
            if (queryResult.next()) {
                professor = new Professor(queryResult);
            }

            return professor;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public List<Professor> findAll() throws SQLException, ConnectionException {
        List<Professor> professores = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDALL_COMMAND);

            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                professores.add(new Professor(queryResult));
            }

            return professores;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    public List<AlunosProfessorDto> findAlunosByProfessor(Long idProfessor) throws SQLException, ConnectionException {
        List<AlunosProfessorDto> alunos = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FIND_ALUNOS_BY_PROFESSOR);
            ps.setLong(1, idProfessor);
            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                alunos.add(new AlunosProfessorDto(queryResult));
            }

            return alunos;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public void update(ProfessorDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, dto.nome);
            ps.setString(2, dto.email);
            ps.setString(3, DaoUtil.toBCryptHash(dto.senha));
            ps.setString(4, dto.usuario); // Alterado de dto.cpf para dto.usuario
            ps.setLong(5, Long.parseLong(dto.idMateria));
            ps.setLong(6, Long.parseLong(dto.id));

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    @Override
    public void delete(ProfessorDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
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

    /**
     * Altera a senha de um professor pelo id.
     *
     * @param idOrigem  id do professor
     * @param novaSenha nova senha já hasheada com BCrypt
     */
    public void alterarSenhaPorId(long idOrigem, String novaSenha) throws SQLException, ConnectionException {
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_PASSWORD);
            ps.setString(1, DaoUtil.toBCryptHash(novaSenha));
            ps.setLong(2, idOrigem);
            ps.executeUpdate();

        } finally {
            DaoUtil.closeResources(ps);
        }
    }
}