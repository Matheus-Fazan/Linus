package com.linus.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminPerfilDto {

    private long id;
    private String nome;
    private String email;
    private String cpf;

    public AdminPerfilDto(ResultSet rs) throws SQLException {
        this.id    = rs.getLong("id");
        this.nome  = rs.getString("nome");
        this.email = rs.getString("email");
        this.cpf   = rs.getString("cpf");
    }

    public long getId()      { return id; }
    public String getNome()  { return nome; }
    public String getEmail() { return email; }
    public String getCpf()   { return cpf; }
}
