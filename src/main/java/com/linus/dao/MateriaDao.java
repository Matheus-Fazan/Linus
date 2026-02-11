package com.linus.dao;

import com.linus.exception.ConnectionException;
import com.linus.exception.NoRegistersAlteredException;
import com.linus.infra.ConnectionManager;
import com.linus.model.dao.Materia;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDao implements GenericDaoInterface<Materia> {

    // sql statements
    private final String SQL_SAVE_COMMAND = "INSERT INTO materia(nome) VALUES(?) RETURNING id";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM materia WHERE id = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM materia";
    private final String SQL_UPDATE_COMMAND = "UPDATE materia SET nome = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM materia WHERE id = ?";

    @Override
    public Materia save(Materia materia) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, materia.getNome());

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                materia.setId(queryResult.getLong("id"));
            }

            return materia;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Materia findById(long id) throws SQLException, ConnectionException {
        Materia materia = null;
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, id);

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                materia = new Materia(queryResult);
            }

            return materia;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public List<Materia> findAll() throws SQLException, ConnectionException {
        List<Materia> materias = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDALL_COMMAND);

            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                materias.add(new Materia(queryResult));
            }

            return materias;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public void update(Materia materia) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, materia.getNome());
            ps.setLong(2, materia.getId());

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