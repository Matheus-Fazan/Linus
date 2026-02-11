package com.linus.dao;

import com.linus.exception.ConnectionException;
import com.linus.exception.NoRegistersAlteredException;
import com.linus.infra.ConnectionManager;
import com.linus.model.dao.Turma;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDao implements GenericDaoInterface<Turma> {

    // sql statements
    private final String SQL_SAVE_COMMAND = "INSERT INTO turma(nome) VALUES(?) RETURNING id";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM turma WHERE id = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM turma";
    private final String SQL_UPDATE_COMMAND = "UPDATE turma SET nome = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM turma WHERE id = ?";

    @Override
    public Turma save(Turma turma) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, turma.getNome());

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                turma.setId(queryResult.getLong("id"));
            }

            return turma;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Turma findById(long id) throws SQLException, ConnectionException {
        Turma turma = null;
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, id);

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                turma = new Turma(queryResult);
            }

            return turma;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public List<Turma> findAll() throws SQLException, ConnectionException {
        List<Turma> turmas = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDALL_COMMAND);

            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                turmas.add(new Turma(queryResult));
            }

            return turmas;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public void update(Turma turma) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, turma.getNome());
            ps.setLong(2, turma.getId());

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