package com.linus.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe modelo que abstrai registros da view {@code ACESSO}
 */
@Data
public class Acesso {

    private long idOrigem;
    private Cargo cargo;
    private String email;
    private String hashSenha;

    /**
     * Construtor da classe que utiliza diretamente objeto {@code ResultSet}.
     * @param rs Objeto {@code ResultSet} resultante da consulta ao banco de dados.
     * @throws SQLException Caso falha SQL
     */
    public Acesso(ResultSet rs) throws SQLException {
        this.email = rs.getString("email");
        this.hashSenha = rs.getString("hash_senha");
        this.cargo = Cargo.valueOf(rs.getString("cargo"));
        this.idOrigem = rs.getLong("id_origem");
    }
}
