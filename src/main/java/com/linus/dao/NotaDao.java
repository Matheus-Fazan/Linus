package com.linus.dao;

import com.linus.exception.ConnectionException;
import com.linus.exception.NoRegistersAlteredException;
import com.linus.infra.ConnectionManager;
import com.linus.model.dao.Nota;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDao implements GenericDaoInterface<Nota> {

    // sql statements
    private final String SQL_SAVE_COMMAND = "INSERT INTO nota(n1, n2, media, id_professor, id_aluno) VALUES(?, ?, ?, ?, ?) RETURNING id";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM nota WHERE id = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM nota";
    private final String SQL_UPDATE_COMMAND = "UPDATE nota SET n1 = ?, n2 = ?, media = ?, id_professor = ?, id_aluno = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM nota WHERE id = ?";

    @Override
    public Nota save(Nota nota) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setBigDecimal(1, nota.getN1());
            ps.setBigDecimal(2, nota.getN2());
            ps.setBigDecimal(3, nota.getMedia());
            ps.setLong(4, nota.getIdProfessor());
            ps.setLong(5, nota.getIdAluno());

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                nota.setId(queryResult.getLong("id"));
            }

            return nota;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Nota findById(long id) throws SQLException, ConnectionException {
        Nota nota = null;
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, id);

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                nota = new Nota(queryResult);
            }

            return nota;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public List<Nota> findAll() throws SQLException, ConnectionException {
        List<Nota> notas = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDALL_COMMAND);

            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                notas.add(new Nota(queryResult));
            }

            return notas;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public void update(Nota nota) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setBigDecimal(1, nota.getN1());
            ps.setBigDecimal(2, nota.getN2());
            ps.setBigDecimal(3, nota.getMedia());
            ps.setLong(4, nota.getIdProfessor());
            ps.setLong(5, nota.getIdAluno());
            ps.setLong(6, nota.getId());

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