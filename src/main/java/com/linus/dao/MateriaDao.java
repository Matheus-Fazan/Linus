package com.linus.dao;

import com.linus.dto.MateriaDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.exception.dao.NoRegistersAlteredException;
import com.linus.infra.connection.ConnectionManager;
import com.linus.model.Materia;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDao implements GenericDaoInterface<Materia, MateriaDto> {

    private final String SQL_SAVE_COMMAND = "INSERT INTO materia(nome) VALUES(?) RETURNING id";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM materia WHERE id = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM materia";
    private final String SQL_UPDATE_COMMAND = "UPDATE materia SET nome = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM materia WHERE id = ?";

    @Override
    public Materia save(MateriaDto dto) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, dto.nome);

            queryResult = ps.executeQuery();

            Materia materia = null;
            if (queryResult.next()) {
                materia = new Materia(queryResult);
            }

            return materia;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Materia findById(MateriaDto dto) throws SQLException, ConnectionException {
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, Long.parseLong(dto.id));

            queryResult = ps.executeQuery();

            Materia materia = null;
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

        try (Connection con = ConnectionManager.connect()) {
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
    public void update(MateriaDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, dto.nome);
            ps.setLong(2, Long.parseLong(dto.id));

            if (ps.executeUpdate() < 1) {
                throw new NoRegistersAlteredException();
            }
        } finally {
            DaoUtil.closeResources(ps);
        }
    }

    @Override
    public void delete(MateriaDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
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
}