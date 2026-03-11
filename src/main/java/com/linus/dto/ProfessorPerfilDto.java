package com.linus.dto;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorPerfilDto {

    public String nome;
    public String usuario;
    public String email;
    @Getter
    private String disciplina;

    public ProfessorPerfilDto(ResultSet rs) throws SQLException {
        this.nome       = rs.getString("nome");
        this.usuario    = rs.getString("usuario");
        this.email      = rs.getString("email");
        this.disciplina = rs.getString("disciplina");
    }

}