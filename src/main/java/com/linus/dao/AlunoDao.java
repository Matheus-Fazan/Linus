package com.linus.dao;

import com.linus.infra.ConnectionManager;
import com.linus.model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao implements GenericDaoInterface<Aluno> {

    // sql statements
    private String SQL_SAVE_COMMAND = "INSERT INTO aluno(email, nome, cpf, hash_senha, id_turma) VALUES(?, ?, ?, ?, ?) RETURNING matricula";
    private String SQL_FINDBYID_COMMAND = "SELECT * FROM aluno WHERE matricula = ?";
    private String SQL_FINDALL_COMMAND = "SELECT * FROM aluno";
    private String SQL_UPDATE_COMMAND = "UPDATE aluno SET email = ?, nome = ?, cpf = ?, hash_senha = ?, id_turma = ? WHERE matricula = ?";
    private String SQL_DELETE_COMMAND = "DELETE FROM aluno WHERE matricula = ?";

    @Override
    public Aluno save(Aluno aluno) throws SQLException, ClassNotFoundException {

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, aluno.getEmail());
            ps.setString(2, aluno.getNome());
            ps.setString(3, aluno.getCpf());
            ps.setString(4, aluno.getHashSenha());
            ps.setLong(5, aluno.getId_turma());

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                aluno.setMatricula(queryResult.getLong("matricula"));
            }
        } catch (Exception e) {
            throw e;
        }

        return aluno;
    }

    @Override
    public Aluno findById(long matricula) throws SQLException, ClassNotFoundException {
        Aluno aluno = null;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, matricula);

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                aluno = new Aluno(queryResult);
            }
        } catch (Exception e) {
            throw e;
        }

        return aluno;
    }

    @Override
    public List<Aluno> findAll() throws SQLException, ClassNotFoundException {
        List<Aluno> alunos = new ArrayList<Aluno>();

        try(Connection con = ConnectionManager.connect()) {
            Statement st = con.createStatement();

            ResultSet queryResult = st.executeQuery(SQL_FINDALL_COMMAND);

            while (queryResult.next()) {
                alunos.add(new Aluno(queryResult));
            }
        } catch (Exception e) {
            throw e;
        }

        return alunos;
    }

    @Override
    public boolean update(Aluno aluno) throws SQLException, ClassNotFoundException {
        boolean success = false;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, aluno.getEmail());
            ps.setString(2, aluno.getNome());
            ps.setString(3, aluno.getCpf());
            ps.setString(4, aluno.getHashSenha());
            ps.setLong(5, aluno.getId_turma());
            ps.setLong(6, aluno.getMatricula());

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        }

        return success;
    }

    @Override
    public boolean delete(long matricula) throws SQLException, ClassNotFoundException {
        boolean success = false;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_DELETE_COMMAND);

            ps.setLong(1, matricula);

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw e;
        }

        return success;
    }
}