package com.linus.dto;

import java.util.Map;

/**
 * Classe de DTO que abstrai os dados de Turma
 */
public class TurmaDto {

    public String id;
    public String nome;

    public TurmaDto(Map<String, String> requestParams) {
        this.id = requestParams.get("id");
        this.nome = requestParams.get("nome");
    }
}