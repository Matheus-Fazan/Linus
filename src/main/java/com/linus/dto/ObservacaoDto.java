package com.linus.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Classe de DTO que abstrai os dados de Observacao
 */
public class ObservacaoDto {

    public String id;
    public String observacao;
    public String idProfessor;
    public String idAluno;
    public String materia;
    public String nomeProfessor;
    public String dataPublicacao;


    public ObservacaoDto(Map<String, String> requestParams) {
        this.id = requestParams.get("id");
        this.observacao = requestParams.get("observacao");
        this.idProfessor = requestParams.get("id_professor");
        this.idAluno = requestParams.get("id_aluno");
        this.nomeProfessor = requestParams.get("nome_professor");
        this.materia = requestParams.get("materia_professor");
        this.dataPublicacao = requestParams.get("data_publicacao");
    }

    public ObservacaoDto(ResultSet queryResult) throws SQLException {;
        this.observacao = queryResult.getString("observacao");
        this.nomeProfessor = queryResult.getString("nome_professor");
        this.materia = queryResult.getString("materia_professor");
        this.dataPublicacao = queryResult.getString("data_publicacao");
    }
}