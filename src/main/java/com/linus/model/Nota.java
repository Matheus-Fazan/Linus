package com.linus.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe modelo que abstrai registros da tabela {@code NOTA}
 */
@Data
public class Nota {

    private long id;
    private BigDecimal n1;
    private BigDecimal n2;
    private BigDecimal media;
    private long idProfessor;
    private long idAluno;

    /**
     * Construtor da classe que utiliza diretamente objeto {@code ResultSet}.
     * @param rs Objeto {@code ResultSet} resultante da consulta ao banco de dados.
     * @throws SQLException Caso falha SQL
     */
    public Nota(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.n1 = rs.getBigDecimal("n1");
        this.n2 = rs.getBigDecimal("n2");
        this.media = rs.getBigDecimal("media");
        this.idProfessor = rs.getLong("id_professor");
        this.idAluno = rs.getLong("id_aluno");
    }
}
