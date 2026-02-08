package com.linus.dao;

import com.linus.infra.ConnectionManager;
import com.linus.model.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDao implements GenericDaoInterface<Professor> {

    // sql statements
    private String SQL_SAVE_COMMAND = "INSERT INTO professor(nome, email, hash_senha, cpf, id_materia) VALUES(?, ?, ?, ?, ?) RETURNING id";
    private String SQL_FINDBYID_COMMAND = "SELECT * FROM professor WHERE id = ?";
    private String SQL_FINDALL_COMMAND = "SELECT * FROM professor";
    private String SQL_UPDATE_COMMAND = "UPDATE professor SET nome = ?, email = ?, hash_senha = ?, cpf = ?, id_materia = ? WHERE id = ?";
    private String SQL_DELETE_COMMAND = "DELETE FROM professor WHERE id = ?";

    @Override
    public Professor save(Professor professor) throws SQLException, ClassNotFoundException {

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, professor.getNome());
            ps.setString(2, professor.getEmail());
            ps.setString(3, professor.getHashSenha());
            ps.setString(4, professor.getCpf());
            ps.setLong(5, professor.getIdMateria());

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                professor.setId(queryResult.getLong("id"));
            }
        } catch (Exception e) {
            throw e;
        }

        return professor;
    }

    @Override
    public Professor findById(long id) throws SQLException, ClassNotFoundException {
        Professor professor = null;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, id);

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                professor = new Professor(queryResult);
            }
        } catch (Exception e) {
            throw e;
        }

        return professor;
    }

    @Override
    public List<Professor> findAll() throws SQLException, ClassNotFoundException {
        List<Professor> professores = new ArrayList<Professor>();

        try(Connection con = ConnectionManager.connect()) {
            Statement st = con.createStatement();

            ResultSet queryResult = st.executeQuery(SQL_FINDALL_COMMAND);

            while (queryResult.next()) {
                professores.add(new Professor(queryResult));
            }
        } catch (Exception e) {
            throw e;
        }

        return professores;
    }

    @Override
    public boolean update(Professor professor) throws SQLException, ClassNotFoundException {
        boolean success = false;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, professor.getNome());
            ps.setString(2, professor.getEmail());
            ps.setString(3, professor.getHashSenha());
            ps.setString(4, professor.getCpf());
            ps.setLong(5, professor.getIdMateria());
            ps.setLong(6, professor.getId());

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