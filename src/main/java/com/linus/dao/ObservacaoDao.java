package com.linus.dao;

import com.linus.exception.ConnectionException;
import com.linus.exception.NoRegistersAlteredException;
import com.linus.infra.ConnectionManager;
import com.linus.model.Observacao;
import com.linus.utils.DaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObservacaoDao implements GenericDaoInterface<Observacao> {

    // sql statements
    private final String SQL_SAVE_COMMAND = "INSERT INTO observacao(observacao, id_professor, id_aluno) VALUES(?, ?, ?) RETURNING id";
    private final String SQL_FINDBYID_COMMAND = "SELECT * FROM observacao WHERE id = ?";
    private final String SQL_FINDALL_COMMAND = "SELECT * FROM observacao";
    private final String SQL_UPDATE_COMMAND = "UPDATE observacao SET observacao = ?, id_professor = ?, id_aluno = ? WHERE id = ?";
    private final String SQL_DELETE_COMMAND = "DELETE FROM observacao WHERE id = ?";

    @Override
    public Observacao save(Observacao observacao) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, observacao.getObservacao());
            ps.setLong(2, observacao.getIdProfessor());
            ps.setLong(3, observacao.getIdAluno());

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                observacao.setId(queryResult.getLong("id"));
            }

            return observacao;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public Observacao findById(long id) throws SQLException, ConnectionException {
        Observacao observacao = null;
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, id);

            queryResult = ps.executeQuery();

            if (queryResult.next()) {
                observacao = new Observacao(queryResult);
            }

            return observacao;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public List<Observacao> findAll() throws SQLException, ConnectionException {
        List<Observacao> observacoes = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet queryResult = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FINDALL_COMMAND);

            queryResult = ps.executeQuery();

            while (queryResult.next()) {
                observacoes.add(new Observacao(queryResult));
            }

            return observacoes;
        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    @Override
    public void update(Observacao observacao) throws SQLException, ConnectionException, NoRegistersAlteredException {
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, observacao.getObservacao());
            ps.setLong(2, observacao.getIdProfessor());
            ps.setLong(3, observacao.getIdAluno());
            ps.setLong(4, observacao.getId());

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