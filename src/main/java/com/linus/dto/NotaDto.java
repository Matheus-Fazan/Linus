package com.linus.dto;

import java.util.Map;

/**
 * Classe de DTO que abstrai os dados de Nota
 */
public class NotaDto {

    public String id;
    public String n1;
    public String n2;
    public String media;
    public String idProfessor;
    public String idAluno;

    public NotaDto(Map<String, String> requestParams) {
        this.id = requestParams.get("id");
        this.n1 = requestParams.get("n1");
        this.n2 = requestParams.get("n2");
        this.media = requestParams.get("media");
        this.idProfessor = requestParams.get("id_professor");
        this.idAluno = requestParams.get("id_aluno");
    }
}