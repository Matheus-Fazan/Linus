package com.linus.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListarAlunosDto {

    public String matricula;
    public String nome;
    public String turma;
    public String n1;
    public String n2;

    public ListarAlunosDto(ResultSet rs) throws SQLException {
        this.matricula = rs.getString("matricula");
        this.nome = rs.getString("nome");
        this.turma = rs.getString("turma");
        this.n1 = rs.getString("n1");
        this.n2 = rs.getString("n2");
    }
}
