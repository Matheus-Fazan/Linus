package com.linus.model.dao;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe modelo que abstrai registros da tabela {@code PROFESSOR}
 */
@Data
public class Professor {

    private long id;
    private String nome;
    private String email;
    private String hashSenha;
    private String cpf;
    private long idMateria;

    /**
     * Construtor da classe que utiliza diretamente objeto {@code ResultSet}.
     * @param rs Objeto {@code ResultSet} resultante da consulta ao banco de dados.
     * @throws SQLException Caso falha SQL
     */
    public Professor(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.nome = rs.getString("nome");
        this.email = rs.getString("email");
        this.hashSenha = rs.getString("hash_senha");
        this.cpf = rs.getString("cpf");
        this.idMateria = rs.getLong("id_materia");
    }
}
