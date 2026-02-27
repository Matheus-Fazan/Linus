package com.linus.dao;

import com.linus.exception.dao.ConnectionException;
import com.linus.infra.connection.ConnectionManager;
import com.linus.model.Usuario;
import com.linus.utils.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcessoDAO {
    private final String SQL_FIND_BY_EMAIL = """
            SELECT cargo, email, hash_senha, id_origem
            FROM   acesso
            WHERE  email = ?
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
            System.out.println(hashSenha);

            if (!org.mindrot.jbcrypt.BCrypt.checkpw(senha, hashSenha)) {
                return null;
            }

            String cargo = queryResult.getString("cargo");
            int id = queryResult.getInt("id_origem");

            String nome = findNameByCargo(cargo, id);

            return new Usuario(id, nome, queryResult.getString("email"), cargo, cargo);

        } finally {
            DaoUtil.closeResources(ps, queryResult);
        }
    }

    private String findNameByCargo(String cargo, int id) throws SQLException {
        final String SQL_FIND_NAME_BY_CARGO = """
                   SELECT %s
                   FROM   %s
                   WHERE  %s = ?
                """;
        String table = switch (cargo.toLowerCase()) {
            case "admin" -> "Admin";
            case "professor" -> "Professor";
            case "aluno" -> "Aluno";
            default -> throw new IllegalArgumentException("Cargo desconhecido: " + cargo);
        };

        String colId = cargo.equalsIgnoreCase("aluno") ? "matricula" : "id";
        String colName = cargo.equalsIgnoreCase("aluno") ? "email" : "nome";


        String sql = String.format(SQL_FIND_NAME_BY_CARGO, colName, table, colId);
        try (Connection con = ConnectionManager.connect()) {
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        return rs.next() ? rs.getString(colName) : null;
                    }
                }
            }
    }
}
