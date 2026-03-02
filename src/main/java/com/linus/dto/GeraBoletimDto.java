package com.linus.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeraBoletimDto {

    private String nomeAluno;
    private String turma;
    private String materia;
    private double nota1;
    private double nota2;
    private double media;
    private String situacao;
    private String observacao;

    public GeraBoletimDto(ResultSet rs) throws SQLException {
        this.nomeAluno   = rs.getString("nome_aluno");
        this.turma       = rs.getString("turma");
        this.materia     = rs.getString("materia");
        this.nota1       = rs.getDouble("n1");
        this.nota2       = rs.getDouble("n2");
        this.media       = rs.getDouble("media");
        this.situacao    = rs.getString("situacao");
        this.observacao  = rs.getString("observacao");
    }

    public String getNomeAluno()  { return nomeAluno; }
    public String getTurma()      { return turma; }
    public String getMateria()    { return materia; }
    public double getNota1()      { return nota1; }
    public double getNota2()      { return nota2; }
    public double getMedia()      { return media; }
    public String getSituacao()   { return situacao; }
    public String getObservacao() { return observacao; }
}
