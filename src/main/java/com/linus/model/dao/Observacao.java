package com.linus.model.dao;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Classe modelo que abstrai registros da tabela {@code OBSERVACAO}
 */
@Data
public class Observacao {

    private long id;
    private String observacao;
    private Timestamp dataCriacao;
    private long idProfessor;
    private long idAluno;

    /**
     * Construtor da classe que utiliza diretamente objeto {@code ResultSet}.
     * @param rs Objeto {@code ResultSet} resultante da consulta ao banco de dados.
     * @throws SQLException Caso falha SQL
     */
    public Observacao(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.observacao = rs.getString("observacao");
        this.dataCriacao = rs.getTimestamp("data_criacao");
        this.idProfessor = rs.getLong("id_professor");
        this.idAluno = rs.getLong("id_aluno");
    }
}
