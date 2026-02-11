package com.linus.dao;

import com.linus.exception.ConnectionException;
import com.linus.exception.NoRegistersAlteredException;
import com.linus.infra.ConnectionManager;
import com.linus.model.dao.Aluno;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao implements GenericDaoInterface<Aluno> {

    // sql statements
    private final String SQL_SAVE_COMMAND = "INSERT INTO aluno(email, nome, cpf, hash_senha, id_turma) VALUES(?, ?, ?, ?, ?) RETURNING matricula";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM aluno WHERE matricula = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM aluno";
    private final String SQL_UPDATE_COMMAND = "UPDATE aluno SET email = ?, nome = ?, cpf = ?, hash_senha = ?, id_turma = ? WHERE matricula = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM aluno WHERE matricula = ?";

    @Override
    public Aluno save(Aluno aluno) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, aluno.getEmail());
            ps.setString(2, aluno.getNome());
            ps.setString(3, aluno.getCpf());
            ps.setString(4, aluno.getHashSenha());
            ps.setLong(5, aluno.getId_turma());

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                aluno.setMatricula(queryResult.getLong("matricula"));
            }

            return aluno;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Aluno findById(long matricula) throws SQLException, ConnectionException {
        Aluno aluno = null;
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, matricula);

            queryResult = ps.executeQuery();

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

        try(Connection con = ConnectionManager.connect()) {
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
    public void update(Aluno aluno) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, aluno.getEmail());
            ps.setString(2, aluno.getNome());
            ps.setString(3, aluno.getCpf());
            ps.setString(4, aluno.getHashSenha());
            ps.setLong(5, aluno.getId_turma());
            ps.setLong(6, aluno.getMatricula());

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    @Override
    public void delete(long matricula) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_DELETE_COMMAND);

            ps.setLong(1, matricula);

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }
}