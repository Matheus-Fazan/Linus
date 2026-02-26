package com.linus.dto;

import java.util.Map;

/**
 * Classe de DTO que abstrai os dados de Materia
 */
public class MateriaDto {

    public String id;
    public String nome;

    public MateriaDto(Map<String, String> requestParams) {
        this.id = requestParams.get("id");
        this.nome = requestParams.get("nome");
    }
}