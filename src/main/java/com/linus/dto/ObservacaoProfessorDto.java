package com.linus.dto;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;


@Getter
public class ObservacaoProfessorDto {
    private Long id;
    private String nomeAluno;
    private String disciplina;
    private String nomeProfessor;
    private String observacao;
    private String dataCriacao;

    public ObservacaoProfessorDto(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.nomeAluno = rs.getString("nome_aluno");
        this.disciplina = rs.getString("disciplina");
        this.nomeProfessor = rs.getString("nome_professor");
        this.observacao = rs.getString("observacao");
        this.dataCriacao = rs.getString("data_criacao");
    }

}

