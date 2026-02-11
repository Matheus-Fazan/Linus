package com.linus.dao;

import com.linus.exception.ConnectionException;
import com.linus.exception.NoRegistersAlteredException;
import com.linus.infra.ConnectionManager;
import com.linus.model.dao.Professor;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDao implements GenericDaoInterface<Professor> {

    // sql statements
    private final String SQL_SAVE_COMMAND = "INSERT INTO professor(nome, email, hash_senha, cpf, id_materia) VALUES(?, ?, ?, ?, ?) RETURNING id";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM professor WHERE id = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM professor";
    private final String SQL_UPDATE_COMMAND = "UPDATE professor SET nome = ?, email = ?, hash_senha = ?, cpf = ?, id_materia = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM professor WHERE id = ?";

    @Override
    public Professor save(Professor professor) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, professor.getNome());
            ps.setString(2, professor.getEmail());
            ps.setString(3, professor.getHashSenha());
            ps.setString(4, professor.getCpf());
            ps.setLong(5, professor.getIdMateria());

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                professor.setId(queryResult.getLong("id"));
            }

            return professor;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Professor findById(long id) throws SQLException, ConnectionException {
        Professor professor = null;
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, id);

            queryResult = ps.executeQuery();

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

        try(Connection con = ConnectionManager.connect()) {
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

    @Override
    public void update(Professor professor) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, professor.getNome());
            ps.setString(2, professor.getEmail());
            ps.setString(3, professor.getHashSenha());
            ps.setString(4, professor.getCpf());
            ps.setLong(5, professor.getIdMateria());
            ps.setLong(6, professor.getId());

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    @Override
    public void delete(long id) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_DELETE_COMMAND);

            ps.setLong(1, id);

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }
}