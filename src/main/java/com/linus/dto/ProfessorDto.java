package com.linus.dto;

import java.util.Map;

/**
 * Classe de DTO que abstrai os dados de Professor
 */
public class ProfessorDto {

    public String id;
    public String nome;
    public String email;
    public String senha;
    public String usuario;
    public String idMateria;

    public ProfessorDto(Map<String, String> requestParams) {
        this.id = requestParams.get("id");
        this.nome = requestParams.get("nome");
        this.email = requestParams.get("email");
        this.senha = requestParams.get("senha");
        this.usuario = requestParams.get("usuario");
        this.idMateria = requestParams.get("id_materia");
    }
}