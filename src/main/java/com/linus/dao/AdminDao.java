package com.linus.dao;

import com.linus.exception.ConnectionException;
import com.linus.exception.NoRegistersAlteredException;
import com.linus.infra.ConnectionManager;
import com.linus.model.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao implements GenericDaoInterface<Admin> {

    // sql statements
    private String SQL_SAVE_COMMAND = "INSERT INTO admin(email, hash_senha) VALUES(?, ?) RETURNING id";
    private String SQL_FINDBYID_COMMAND = "SELECT * FROM admin WHERE id = ?";
    private String SQL_FINDALL_COMMAND = "SELECT * FROM admin";
    private String SQL_UPDATE_COMMAND = "UPDATE admin SET email = ?, hash_senha = ? WHERE id = ?";
    private String SQL_DELETE_COMMAND = "DELETE FROM admin WHERE id = ?";

    @Override
    public Admin save(Admin admin) throws SQLException, ConnectionException {

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, admin.getEmail());
            ps.setString(2, admin.getHashSenha());

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                admin.setId(queryResult.getLong("id"));
            }
        }

        return admin;
    }

    @Override
    public Admin findById(long id) throws SQLException, ConnectionException {
        Admin admin = null;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, id);

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                admin = new Admin(queryResult);
            }
        }

        return admin;
    }

    @Override
    public List<Admin> findAll() throws SQLException, ConnectionException {
        List<Admin> admins = new ArrayList<Admin>();

        try(Connection con = ConnectionManager.connect()) {
            Statement st = con.createStatement();

            ResultSet queryResult = st.executeQuery(SQL_FINDALL_COMMAND);

            while (queryResult.next()) {
                admins.add(new Admin(queryResult));
            }
        }

        return admins;
    }

    @Override
    public void update(Admin admin) throws SQLException, ConnectionException, NoRegistersAlteredException {

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, admin.getEmail());
            ps.setString(2, admin.getHashSenha());
            ps.setLong(3, admin.getId());

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