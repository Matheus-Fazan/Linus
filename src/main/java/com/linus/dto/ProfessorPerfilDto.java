package com.linus.dto;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorPerfilDto {

    @Getter
    private long id;
    public String nome;
    public String usuario;
    public String email;
    @Getter
    private String disciplina;

    public ProfessorPerfilDto(ResultSet rs) throws SQLException {
        this.id         = rs.getLong("id");
        this.nome       = rs.getString("nome");
        this.usuario    = rs.getString("usuario");
        this.email      = rs.getString("email");
        this.disciplina = rs.getString("disciplina");
    }

}