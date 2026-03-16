package com.linus.dto;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class ProfessorVisualizarDto {
    private long id;
    private String nome;
    private String email;
    private String disciplina;

    public ProfessorVisualizarDto(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.nome = rs.getString("nome");
        this.email = rs.getString("email");
        this.disciplina = rs.getString("disciplina");
    }
}
