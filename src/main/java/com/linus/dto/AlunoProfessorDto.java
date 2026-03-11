package com.linus.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoProfessorDto {

    public long idNota;
    public long   matricula;
    public String nome;
    public String turma;
    public double n1;
    public double n2;
    public double media;
    public String observacao;
    public String situacao;

    public AlunoProfessorDto(ResultSet rs) throws SQLException {
        this.idNota    = rs.getLong("id_nota");
        this.matricula = rs.getLong("matricula");
        this.nome      = rs.getString("nome");
        this.turma     = rs.getString("turma");
        this.n1        = rs.getDouble("n1");
        this.n2        = rs.getDouble("n2");
        this.media        = rs.getDouble("media");
        this.observacao   = rs.getString("observacao");
        this.situacao        = rs.getString("situacao");
    }
}