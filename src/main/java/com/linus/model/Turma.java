package com.linus.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe modelo que abstrai registros da tabela {@code TURMA}
 */
@Data
public class Turma {

    private long id;
    private String nome;

    /**
     * Construtor da classe que utiliza diretamente objeto {@code ResultSet}.
     * @param rs Objeto {@code ResultSet} resultante da consulta ao banco de dados.
     * @throws SQLException Caso falha SQL
     */
    public Turma(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.nome = rs.getString("nome");
    }
}
