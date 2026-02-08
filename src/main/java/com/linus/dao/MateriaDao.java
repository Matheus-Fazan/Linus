package com.linus.dao;

import com.linus.infra.ConnectionManager;
import com.linus.model.Materia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDao implements GenericDaoInterface<Materia> {

    // sql statements
    private String SQL_SAVE_COMMAND = "INSERT INTO materia(nome) VALUES(?) RETURNING id";
    private String SQL_FINDBYID_COMMAND = "SELECT * FROM materia WHERE id = ?";
    private String SQL_FINDALL_COMMAND = "SELECT * FROM materia";
    private String SQL_UPDATE_COMMAND = "UPDATE materia SET nome = ? WHERE id = ?";
    private String SQL_DELETE_COMMAND = "DELETE FROM materia WHERE id = ?";

    @Override
    public Materia save(Materia materia) throws SQLException, ClassNotFoundException {

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, materia.getNome());

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                materia.setId(queryResult.getLong("id"));
            }
        } catch (Exception e) {
            throw e;
        }

        return materia;
    }

    @Override
    public Materia findById(long id) throws SQLException, ClassNotFoundException {
        Materia materia = null;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, id);

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                materia = new Materia(queryResult);
            }
        } catch (Exception e) {
            throw e;
        }

        return materia;
    }

    @Override
    public List<Materia> findAll() throws SQLException, ClassNotFoundException {
        List<Materia> materias = new ArrayList<Materia>();

        try(Connection con = ConnectionManager.connect()) {
            Statement st = con.createStatement();

            ResultSet queryResult = st.executeQuery(SQL_FINDALL_COMMAND);

            while (queryResult.next()) {
                materias.add(new Materia(queryResult));
            }
        } catch (Exception e) {
            throw e;
        }

        return materias;
    }

    @Override
    public boolean update(Materia materia) throws SQLException, ClassNotFoundException {
        boolean success = false;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, materia.getNome());
            ps.setLong(2, materia.getId());

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