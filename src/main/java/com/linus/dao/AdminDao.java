package com.linus.dao;

import com.linus.exception.ConnectionException;
import com.linus.exception.NoRegistersAlteredException;
import com.linus.infra.ConnectionManager;
import com.linus.model.dao.Admin;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao implements GenericDaoInterface<Admin> {

    // sql statements
    private final String SQL_SAVE_COMMAND = "INSERT INTO admin(email, hash_senha) VALUES(?, ?) RETURNING id";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM admin WHERE id = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM admin";
    private final String SQL_UPDATE_COMMAND = "UPDATE admin SET email = ?, hash_senha = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM admin WHERE id = ?";

    @Override
    public Admin save(Admin admin) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, admin.getEmail());
            ps.setString(2, admin.getHashSenha());

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                admin.setId(queryResult.getLong("id"));
            }

            return admin;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Admin findById(long id) throws SQLException, ConnectionException {
        Admin admin = null;
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try(Connection con = ConnectionManager.connect()) {

            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, id);

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                admin = new Admin(queryResult);
            }

            return admin;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public List<Admin> findAll() throws SQLException, ConnectionException {
        List<Admin> admins = new ArrayList<>();
        Statement st = null;
        ResultSet queryResult = null;

        try(Connection con = ConnectionManager.connect()) {
             st = con.createStatement();

            queryResult = st.executeQuery(SQL_FINDALL_COMMAND);

            while (queryResult.next()) {
                admins.add(new Admin(queryResult));
            }

            return admins;
        } finally {
            DaoUtil.closeResources(st, queryResult);
        }
    }

    @Override
    public void update(Admin admin) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, admin.getEmail());
            ps.setString(2, admin.getHashSenha());
            ps.setLong(3, admin.getId());

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