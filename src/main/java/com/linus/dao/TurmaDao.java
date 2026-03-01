package com.linus.dao;

import com.linus.dto.TurmaDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.exception.dao.NoRegistersAlteredException;
import com.linus.infra.connection.ConnectionManager;
import com.linus.model.dao.Turma;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDao implements GenericDaoInterface<Turma, TurmaDto> {

    private final String SQL_SAVE_COMMAND = "INSERT INTO turma(nome) VALUES(?) RETURNING id";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM turma WHERE id = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM turma";
    private final String SQL_UPDATE_COMMAND = "UPDATE turma SET nome = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM turma WHERE id = ?";

    @Override
    public Turma save(TurmaDto dto) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, dto.nome);

            queryResult = ps.executeQuery();

            Turma turma = null;
            if (queryResult.next()) {
                turma = new Turma(queryResult);
            }

            return turma;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Turma findById(TurmaDto dto) throws SQLException, ConnectionException {
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, Long.parseLong(dto.id));

            queryResult = ps.executeQuery();

            Turma turma = null;
            if (queryResult.next()) {
                turma = new Turma(queryResult);
            }

            return turma;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public List<Turma> findAll() throws SQLException, ConnectionException {
        List<Turma> turmas = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDALL_COMMAND);

            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                turmas.add(new Turma(queryResult));
            }

            return turmas;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public void update(TurmaDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
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
    public void delete(TurmaDto dto) throws SQLException, ConnectionException, NoRegistersAlteredException {
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