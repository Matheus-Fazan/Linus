package com.linus.dto;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class AlunoVisualizarDto {
    private long matricula;
    private String nome;
    private String email;

    public AlunoVisualizarDto(ResultSet rs) throws SQLException {
        this.matricula = rs.getLong("matricula");
        this.nome = rs.getString("nome");
        this.email = rs.getString("email");
    }
}
