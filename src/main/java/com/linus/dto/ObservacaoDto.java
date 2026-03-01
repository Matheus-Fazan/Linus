package com.linus.dto;

import java.util.Map;

/**
 * Classe de DTO que abstrai os dados de Observacao
 */
public class ObservacaoDto {

    public String id;
    public String observacao;
    public String idProfessor;
    public String idAluno;

    public ObservacaoDto(Map<String, String> requestParams) {
        this.id = requestParams.get("id");
        this.observacao = requestParams.get("observacao");
        this.idProfessor = requestParams.get("id_professor");
        this.idAluno = requestParams.get("id_aluno");
    }
}