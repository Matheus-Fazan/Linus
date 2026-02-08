package com.linus.dao;

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
    public Turma save(Turma turma) throws SQLException, ClassNotFoundException {

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, turma.getNome());

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                turma.setId(queryResult.getLong("id"));
            }
        } catch (Exception e) {
            throw e;
        }

        return turma;
    }

    @Override
    public Turma findById(long id) throws SQLException, ClassNotFoundException {
        Turma turma = null;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, id);

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                turma = new Turma(queryResult);
            }
        } catch (Exception e) {
            throw e;
        }

        return turma;
    }

    @Override
    public List<Turma> findAll() throws SQLException, ClassNotFoundException {
        List<Turma> turmas = new ArrayList<Turma>();

        try(Connection con = ConnectionManager.connect()) {
            Statement st = con.createStatement();

            ResultSet queryResult = st.executeQuery(SQL_FINDALL_COMMAND);

            while (queryResult.next()) {
                turmas.add(new Turma(queryResult));
            }
        } catch (Exception e) {
            throw e;
        }

        return turmas;
    }

    @Override
    public boolean update(Turma turma) throws SQLException, ClassNotFoundException {
        boolean success = false;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, turma.getNome());
            ps.setLong(2, turma.getId());

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        }

        return success;
    }

    @Override
    public boolean delete(long id) throws SQLException, ClassNotFoundException {
        boolean success = false;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_DELETE_COMMAND);

            ps.setLong(1, id);

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        }

        return success;
    }
}