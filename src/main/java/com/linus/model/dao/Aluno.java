package com.linus.model.dao;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe modelo que abstrai registros da tabela {@code ALUNO}
 */
@Data
public class Aluno {

    private long matricula;
    private String email;
    private String nome;
    private String cpf;
    private String hashSenha;
    private long id_turma;

    /**
     * Construtor da classe que utiliza diretamente objeto {@code ResultSet}.
     * @param rs Objeto {@code ResultSet} resultante da consulta ao banco de dados.
     * @throws SQLException Caso falha SQL
     */
    public Aluno(ResultSet rs) throws SQLException {
        this.matricula = rs.getLong("matricula");
        this.email = rs.getString("email");
        this.nome = rs.getString("nome");
        this.cpf = rs.getString("cpf");
        this.hashSenha = rs.getString("hash_senha");
        this.id_turma = rs.getLong("id_turma");
    }
}
