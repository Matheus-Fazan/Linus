package com.linus.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoletimDto {

    public String materia;
    public String n1;
    public String n2;
    public String media;
    public String observacao;

    public BoletimDto(String materia, String n1, String n2, String media, String observacao) {
        this.materia = materia;
        this.n1 = n1;
        this.n2 = n2;
        this.media = media;
        this.observacao = observacao;
    }

    public BoletimDto(ResultSet rs) throws SQLException {
        this.materia = rs.getString("materia");
        this.n1 = rs.getString("n1");
        this.n2 = rs.getString("n2");
        this.media = rs.getString("media");
        this.observacao = rs.getString("observacao");
    }
}
