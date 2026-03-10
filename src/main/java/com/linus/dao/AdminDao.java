package com.linus.dao;

import com.linus.dto.AdminDto;
import com.linus.dto.AdminPerfilDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.exception.dao.NoRegistersAlteredException;
import com.linus.infra.connection.ConnectionManager;
import com.linus.model.dao.Admin;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao implements GenericDaoInterface<Admin, AdminDto> {

    private final String SQL_SAVE_COMMAND = "INSERT INTO admin(email, hash_senha) VALUES(?, ?) RETURNING id";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM admin WHERE id = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM admin";
    private final String SQL_UPDATE_COMMAND = "UPDATE admin SET email = ?, hash_senha = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM admin WHERE id = ?";
    private final String SQL_FIND_PERFIL_BY_ID = """
                SELECT nome, email, hash_senha
                FROM admin
                WHERE id = ?
            """;

    @Override
    public Admin save(AdminDto dto) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, dto.email);
            ps.setString(2, DaoUtil.toBCryptHash(dto.senha));

            queryResult = ps.executeQuery();

            Admin admin = null;
            if (queryResult.next()) {
                admin = new Admin(queryResult);
            }

            return admin;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Admin findById(AdminDto dto) throws SQLException, ConnectionException {
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, Long.parseLong(dto.id));

            queryResult = ps.executeQuery();

            Admin admin = null;
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
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDALL_COMMAND);

            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                admins.add(new Admin(queryResult));
            }

            return admins;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public void update(AdminDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, dto.email);
            ps.setString(2, DaoUtil.toBCryptHash(dto.senha));
            ps.setLong(3, Long.parseLong(dto.id));

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    @Override
    public void delete(AdminDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_DELETE_COMMAND);

            ps.setLong(1, Long.parseLong(dto.id));

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    public AdminPerfilDto findById(long id) throws ConnectionException, SQLException {
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection conn = ConnectionManager.connect()) {
            ps = conn.prepareStatement(SQL_FIND_PERFIL_BY_ID);
            ps.setLong(1, id);

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                return new AdminPerfilDto(queryResult);
            }

            return null;

        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }
}