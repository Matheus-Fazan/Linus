package com.linus.dao;

import com.linus.dto.AlunoDto;
import com.linus.dto.AlunoPerfilDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.exception.dao.NoRegistersAlteredException;
import com.linus.infra.connection.ConnectionManager;
import com.linus.model.dao.Aluno;
import com.linus.model.enums.Situacao;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao implements GenericDaoInterface<Aluno, AlunoDto> {

    private final String SQL_SAVE_COMMAND = "INSERT INTO aluno(email, nome, cpf, hash_senha, id_turma) VALUES(?, ?, ?, ?, ?) RETURNING matricula";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM aluno WHERE matricula = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM aluno";
    private final String SQL_UPDATE_COMMAND = "UPDATE aluno SET email = ?, nome = ?, cpf = ?, hash_senha = ?, id_turma = ? WHERE matricula = ?";
    private final String SQL_UPDATE_WITHOUT_IDTURMA_COMMAND = "UPDATE aluno SET email = ?, nome = ?, cpf = ?, hash_senha = ? WHERE matricula = ?";
    private final String SQL_UPDATE_EMAIL_BY_MATRICULA_COMMAND = "UPDATE aluno SET email = ? WHERE matricula = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM aluno WHERE matricula = ?";
    private final String SQL_FIND_PERFIL_BY_MATRICULA_COMMAND = """
                                SELECT
                                 a.matricula,
                                 a.nome,
                                 a.email,
                                 a.cpf,
                                 CASE
                                     WHEN avg(n.media) IS NULL  THEN 'Em processo'
                                     WHEN avg(n.media) >= 7      THEN 'Aprovado'
                                     ELSE                             'Reprovado'
                                 END AS situacao,
                                 COALESCE(t.nome, 'Sem turma') AS turma
                             FROM aluno a
                             LEFT JOIN turma t ON t.id  = a.id_turma
                             LEFT JOIN nota  n ON n.id_aluno = a.matricula
                             WHERE a.matricula = ?
                             GROUP BY a.matricula, a.nome, a.email, a.cpf, t.nome;
                """;
    private static final String SQL_UPDATE_PASSWORD = """
        UPDATE aluno SET hash_senha = ? WHERE matricula = ?
        """;

    private static final String SQL_FIRST_ACCESS_UPDATE_COMMAND = "UPDATE aluno SET email=?, hash_senha=? WHERE matricula=? AND nome=? AND cpf=?";

    @Override
    public Aluno save(AlunoDto dto) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, dto.email);
            ps.setString(2, dto.nome);
            ps.setString(3, dto.cpf);
            ps.setString(4, DaoUtil.toBCryptHash(dto.senha));
            ps.setLong(5, Long.parseLong(dto.idTurma));

            queryResult = ps.executeQuery();

            Aluno aluno = null;
            if (queryResult.next()) {
                aluno = new Aluno(queryResult);
            }

            return aluno;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    public String previousSave(AlunoDto dto) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;
        String generatedMatricula = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, dto.nome);
            ps.setString(2, dto.cpf);
            ps.setLong(3, Long.parseLong(dto.idTurma));

            queryResult = ps.executeQuery();

            Aluno aluno = null;
            if (queryResult.next()) {
                generatedMatricula = queryResult.getString("matricula");
            }

            return generatedMatricula;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Aluno findById(AlunoDto dto) throws SQLException, ConnectionException {
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, Long.parseLong(dto.matricula));

            queryResult = ps.executeQuery();

            Aluno aluno = null;
            if (queryResult.next()) {
                aluno = new Aluno(queryResult);
            }

            return aluno;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public List<Aluno> findAll() throws SQLException, ConnectionException {
        List<Aluno> alunos = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDALL_COMMAND);

            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                alunos.add(new Aluno(queryResult));
            }

            return alunos;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public void update(AlunoDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, dto.email);
            ps.setString(2, dto.nome);
            ps.setString(3, dto.cpf);
            ps.setString(4, DaoUtil.toBCryptHash(dto.senha));
            ps.setLong(5, Long.parseLong(dto.idTurma));
            ps.setLong(6, Long.parseLong(dto.matricula));

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    public void updateWithoutIdTurma(AlunoDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_WITHOUT_IDTURMA_COMMAND);

            ps.setString(1, dto.email);
            ps.setString(2, dto.nome);
            ps.setString(3, dto.cpf);
            ps.setString(4, DaoUtil.toBCryptHash(dto.senha));
            ps.setLong(5, Long.parseLong(dto.matricula));

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    public void updateEmailByMatricula(AlunoDto dto)
            throws SQLException, ConnectionException, NoRegistersAlteredException {

        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_EMAIL_BY_MATRICULA_COMMAND);

            ps.setString(1, dto.email);
            ps.setLong(2, Long.parseLong(dto.matricula));

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    @Override
    public void delete(AlunoDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_DELETE_COMMAND);

            ps.setLong(1, Long.parseLong(dto.matricula));

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    /**
     * Busca os dados de perfil de um aluno pela sua matrícula.
     * <p>
     * Retorna: matricula, nome, email, cpf, situacao (ativo/inativo baseado em
     * id_turma) e o nome da turma à qual o aluno pertence.
     *
     * @param matricula matrícula do aluno a ser buscado
     * @return objeto {@link AlunoPerfilDto} com os dados do aluno, ou {@code null} se não encontrado
     * @throws ConnectionException caso não seja possível obter uma conexão com o banco
     * @throws SQLException        caso ocorra erro na execução da query
     */
    public AlunoPerfilDto findByMatricula(long matricula) throws ConnectionException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try (Connection conn = ConnectionManager.connect()) {
            ps = conn.prepareStatement(SQL_FIND_PERFIL_BY_MATRICULA_COMMAND);
            ps.setLong(1, matricula);

            rs = ps.executeQuery();

            if (rs.next()) {
                return new AlunoPerfilDto(rs);
            }

            return null;

        } finally {
            DaoUtil.closeResources(ps, rs);
        }
    }

    /**
     * Altera a senha de um aluno pelo email.
     *
     * @param idOrigem  matricula do aluno
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

    public void firstAccess(AlunoDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException{

        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FIRST_ACCESS_UPDATE_COMMAND);

            ps.setString(1, dto.email);
            ps.setString(2, DaoUtil.toBCryptHash(dto.senha));
            ps.setLong(3, Long.parseLong(dto.matricula));
            ps.setString(4, dto.nome);
            ps.setString(5, dto.cpf);

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();

            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }
}