package com.linus.dao;

import com.linus.exception.ConnectionException;
import com.linus.exception.NoRegistersAlteredException;
import com.linus.infra.ConnectionManager;
import com.linus.model.Turma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDao implements GenericDaoInterface<Turma> {

    // sql statements
    private String SQL_SAVE_COMMAND = "INSERT INTO turma(nome) VALUES(?) RETURNING id";
    private String SQL_FINDBYID_COMMAND = "SELECT * FROM turma WHERE id = ?";
    private String SQL_FINDALL_COMMAND = "SELECT * FROM turma";
    private String SQL_UPDATE_COMMAND = "UPDATE turma SET nome = ? WHERE id = ?";
    private String SQL_DELETE_COMMAND = "DELETE FROM turma WHERE id = ?";

    @Override
    public Turma save(Turma turma) throws SQLException, ConnectionException {

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, turma.getNome());

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                turma.setId(queryResult.getLong("id"));
            }
        }

        return turma;
    }

    @Override
    public Turma findById(long id) throws SQLException, ConnectionException {
        Turma turma = null;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, id);

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                turma = new Turma(queryResult);
            }
        }

        return turma;
    }

    @Override
    public List<Turma> findAll() throws SQLException, ConnectionException {
        List<Turma> turmas = new ArrayList<Turma>();

        try(Connection con = ConnectionManager.connect()) {
            Statement st = con.createStatement();

            ResultSet queryResult = st.executeQuery(SQL_FINDALL_COMMAND);

            while (queryResult.next()) {
                turmas.add(new Turma(queryResult));
            }
        }

        return turmas;
    }

    @Override
    public void update(Turma turma) throws SQLException, ConnectionException, NoRegistersAlteredException {

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, turma.getNome());
            ps.setLong(2, turma.getId());

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        }
    }

    @Override
    public void delete(long id) throws SQLException, ConnectionException, NoRegistersAlteredException {

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_DELETE_COMMAND);

            ps.setLong(1, id);

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        }

    }
}