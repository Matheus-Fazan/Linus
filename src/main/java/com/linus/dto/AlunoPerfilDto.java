package com.linus.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoPerfilDto {

    private long matricula;
    private String nome;
    private String email;
    private String cpf;
    private String situacao;
    private String turma;

    public AlunoPerfilDto(ResultSet rs) throws SQLException {
        this.matricula = rs.getLong("matricula");
        this.nome      = rs.getString("nome");
        this.email     = rs.getString("email");
        this.cpf       = rs.getString("cpf");
        this.situacao  = rs.getString("situacao");
        this.turma     = rs.getString("turma");
    }

    public long getMatricula() { return matricula; }
    public String getNome()    { return nome; }
    public String getEmail()   { return email; }
    public String getCpf()     { return cpf; }
    public String getSituacao(){ return situacao; }
    public String getTurma()   { return turma; }
}






