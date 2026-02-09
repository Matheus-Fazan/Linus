package com.linus.dao;

import com.linus.exception.ConnectionException;
import com.linus.exception.NoRegistersAlteredException;
import com.linus.infra.ConnectionManager;
import com.linus.model.Observacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObservacaoDao implements GenericDaoInterface<Observacao> {

    // sql statements
    private String SQL_SAVE_COMMAND = "INSERT INTO observacao(observacao, id_professor, id_aluno) VALUES(?, ?, ?) RETURNING id";
    private String SQL_FINDBYID_COMMAND = "SELECT * FROM observacao WHERE id = ?";
    private String SQL_FINDALL_COMMAND = "SELECT * FROM observacao";
    private String SQL_UPDATE_COMMAND = "UPDATE observacao SET observacao = ?, id_professor = ?, id_aluno = ? WHERE id = ?";
    private String SQL_DELETE_COMMAND = "DELETE FROM observacao WHERE id = ?";

    @Override
    public Observacao save(Observacao observacao) throws SQLException, ConnectionException {

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_COMMAND);

            ps.setString(1, observacao.getObservacao());
            ps.setLong(2, observacao.getIdProfessor());
            ps.setLong(3, observacao.getIdAluno());

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                observacao.setId(queryResult.getLong("id"));
            }
        }

        return observacao;
    }

    @Override
    public Observacao findById(long id) throws SQLException, ConnectionException {
        Observacao observacao = null;

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_FINDBYID_COMMAND);

            ps.setLong(1, id);

            ResultSet queryResult = ps.executeQuery();

            if (queryResult.next()) {
                observacao = new Observacao(queryResult);
            }
        }

        return observacao;
    }

    @Override
    public List<Observacao> findAll() throws SQLException, ConnectionException {
        List<Observacao> observacoes = new ArrayList<Observacao>();

        try(Connection con = ConnectionManager.connect()) {
            Statement st = con.createStatement();

            ResultSet queryResult = st.executeQuery(SQL_FINDALL_COMMAND);

            while (queryResult.next()) {
                observacoes.add(new Observacao(queryResult));
            }
        }

        return observacoes;
    }

    @Override
    public void update(Observacao observacao) throws SQLException, ConnectionException, NoRegistersAlteredException {

        try(Connection con = ConnectionManager.connect()) {
            PreparedStatement ps = con.prepareStatement(SQL_UPDATE_COMMAND);

            ps.setString(1, observacao.getObservacao());
            ps.setLong(2, observacao.getIdProfessor());
            ps.setLong(3, observacao.getIdAluno());
            ps.setLong(4, observacao.getId());

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