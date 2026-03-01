package com.linus.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe modelo que abstrai registros da tabela {@code ADMIN}
 */
@Data
public class Admin {

    private long id;
    private String email;
    private String hashSenha;

    /**
     * Construtor da classe que utiliza diretamente objeto {@code ResultSet}.
     * @param rs Objeto {@code ResultSet} resultante da consulta ao banco de dados.
     * @throws SQLException Caso falha SQL
     */
    public Admin(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.email = rs.getString("email");
        this.hashSenha = rs.getString("hash_senha");
    }
}
