package com.linus.dao;

import com.linus.exception.dao.ConnectionException;
import com.linus.infra.connection.ConnectionManager;
import com.linus.model.dao.Usuario;
import com.linus.utils.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AcessoDAO {
    private static final String SQL_FIND_BY_EMAIL = """
            SELECT cargo, email, hash_senha, id_origem
            FROM   acesso
            WHERE  email = ?
       """;
    private static final String SQL_FIND_NAME_BY_CARGO = """
                   SELECT %s
                   FROM   %s
                   WHERE  %s = ?
                """;

    private static final String SQL_FIND_CARGO_BY_EMAIL = """
        SELECT id_origem, cargo FROM acesso WHERE email = ?
        """;

    public Usuario auth(String email, String senha) throws SQLException, ConnectionException {
        ResultSet queryResult = null;
        PreparedStatement ps = null;

        try(Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FIND_BY_EMAIL);
            ps.setString(1, email);
            queryResult = ps.executeQuery();
            if (!queryResult.next()) {
                return null;
            }

            String hashSenha = queryResult.getString("hash_senha");

            if (!org.mindrot.jbcrypt.BCrypt.checkpw(senha, hashSenha)) {
                return null;
            }

            String cargo = queryResult.getString("cargo");
            long id = queryResult.getLong("id_origem");

            String nome = findNameByCargo(cargo, id);

            return new Usuario(id, nome, queryResult.getString("email"), cargo, cargo);

        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    private String findNameByCargo(String cargo, Long id) throws SQLException {
        String table = switch (cargo.toLowerCase()) {
            case "admin" -> "Admin";
            case "professor" -> "Professor";
            case "aluno" -> "Aluno";
            default -> throw new IllegalArgumentException("Cargo desconhecido: " + cargo);
        };

        String colId = cargo.equalsIgnoreCase("aluno") ? "matricula" : "id";
        String colName = "nome";


        String sql = String.format(SQL_FIND_NAME_BY_CARGO, colName, table, colId);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            return rs.next() ? rs.getString(colName) : null;

        } finally {
            DaoUtil.closeResources(ps, rs);
        }
    }

    public Optional<String[]> findCargoByEmail(String email) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try (Connection con = ConnectionManager.connect()) {
            ps = con.prepareStatement(SQL_FIND_CARGO_BY_EMAIL);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new String[]{
                        rs.getString("id_origem"),
                        rs.getString("cargo")
                });
            }

            return Optional.empty();

        } catch (ConnectionException | SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados", e);
        } finally {
            DaoUtil.closeResources(ps, rs);
        }
    }
}
