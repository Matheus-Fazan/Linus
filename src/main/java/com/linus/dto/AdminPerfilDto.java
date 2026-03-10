package com.linus.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminPerfilDto {

    private String nome;
    private String email;

    public AdminPerfilDto(ResultSet rs) throws SQLException {
        this.nome  = rs.getString("nome");
        this.email = rs.getString("email");
    }

    public String getNome()  { return nome; }
    public String getEmail() {return email; }
}
