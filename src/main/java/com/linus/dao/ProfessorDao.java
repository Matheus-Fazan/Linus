package com.linus.dao;

import com.linus.dto.AlunoProfessorDto;
import com.linus.dto.ProfessorDto;
import com.linus.dto.ProfessorPerfilDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.exception.dao.NoRegistersAlteredException;
import com.linus.infra.connection.ConnectionManager;
import com.linus.model.dao.Professor;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDao implements GenericDaoInterface<Professor, ProfessorDto> {

    private final String SQL_SAVE_COMMAND = "INSERT INTO professor(nome, email, hash_senha, usuario, id_materia) VALUES(?, ?, ?, ?, ?) RETURNING id";
    private final String SQL_FINDBYID_COMMAND = """
                SELECT
                    p.nome,
                    p.usuario,
                    p.email,
                    m.nome AS disciplina
                FROM professor p
                JOIN materia m ON m.id = p.id_materia
                WHERE p.id = ?
                """;
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM professor";
    private static final String SQL_FIND_ALUNOS = """
            SELECT
              n.id AS id_nota,
              a.matricula,
              m.nome as materia,
              a.nome,
              COALESCE(t.nome, 'Sem turma') AS turma,
              n.n1,
              n.n2,
              n.media,
              n.observacao,
              CASE
                   WHEN avg(n.media) IS NULL  THEN 'Em processo'
                   WHEN avg(n.media) >= 7      THEN 'Aprovado'
                   ELSE                             'Reprovado'
              END AS situacao,
             (n.id_professor = ?) AS pertence_ao_professor
          FROM nota n
          JOIN aluno    a ON a.matricula = n.id_aluno
          LEFT JOIN turma t ON t.id      = a.id_turma
          left join professor p on p.id = n.id_professor
          left join materia m on p.id_materia = m.id
          GROUP BY a.matricula,
              a.nome,
              n.id,
              materia,
              turma,
              n.n1,
              n.n2,
              n.media,
              n.observacao
         ORDER by a.nome;
            """;
    private final String SQL_UPDATE_COMMAND = "UPDATE professor SET nome = ?, email = ?, hash_senha = ?, usuario = ?, id_materia = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM professor WHERE id = ?";
    private static final String SQL_UPDATE_PASSWORD = """
        UPDATE professor SET hash_senha = ? WHERE id = ?
        """;
    private static final String  SQL_FIND_ALUNOS_BY_MATRICULA_E_PROFESSOR = """
                SELECT
                    n.id AS id_nota,
                    a.matricula,
                    a.nome,
                    m.nome as materia,
                    COALESCE(t.nome, 'Sem turma') AS turma,
                    n.n1,
                    n.n2,
                    n.media,
                    n.observacao,
                    CASE
                         WHEN avg(n.media) IS NULL  THEN 'Em processo'
                         WHEN avg(n.media) >= 7      THEN 'Aprovado'
                         ELSE                             'Reprovado'
                    END AS situacao,
                    (n.id_professor = ?) AS pertence_ao_professor
                FROM nota n
                JOIN aluno    a ON a.matricula = n.id_aluno
                LEFT JOIN turma t ON t.id      = a.id_turma
                LEFT JOIN professor p ON p.id = n.id_professor
                LEFT JOIN materia m ON p.id_materia = m.id
                WHERE n.id_aluno    = ?    
                GROUP BY a.matricula,
                    a.nome,
                    n.id,
                    n.media,
                    materia,
                    turma,
                    n.n1,
                    n.n2,
                    n.media,
                    n.observacao
               ORDER by a.matricula
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
        return null;
    }

    public ProfessorPerfilDto findById(long idProfessor) throws SQLException, ConnectionException {
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);
            ps.setLong(1, idProfessor);

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                return new ProfessorPerfilDto(queryResult);
            }

            return null;

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

    public List<AlunoProfessorDto> findAlunosByProfessor(Long idProfessor) throws SQLException, ConnectionException {
        List<AlunoProfessorDto> alunos = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FIND_ALUNOS);
            ps.setLong(1, idProfessor);
            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                alunos.add(new AlunoProfessorDto(queryResult));
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
            ps.setString(4, dto.usuario);
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

    public List<AlunoProfessorDto> findByMatricula(long matricula, long idProfessor)
            throws ConnectionException, SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AlunoProfessorDto> resultado = new ArrayList<>();

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FIND_ALUNOS_BY_MATRICULA_E_PROFESSOR);
            ps.setLong(1, matricula);
            ps.setLong(2, idProfessor);

            rs = ps.executeQuery();

            while (rs.next()) {
                resultado.add(new AlunoProfessorDto(rs));
            }

            return resultado;

        } finally {
            DaoUtil.closeResources(ps, rs);
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