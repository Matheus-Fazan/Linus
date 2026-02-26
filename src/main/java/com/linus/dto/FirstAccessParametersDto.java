package com.linus.dto;

import java.util.Map;

/**
 * Classe de DTO que abstrai os parametros de requisicao para servlet {@code FirstAcessServlet}
 */
public class FirstAccessParametersDto {

    public String nome;
    public String email;
    public String senha;
    public String cpf;
    public String matricula;

    public FirstAccessParametersDto(Map<String, String> requestParams) {
        this.nome = requestParams.get("nome");
        this.email = requestParams.get("email");
        this.senha = requestParams.get("senha");
        this.cpf = requestParams.get("cpf");
        this.matricula = requestParams.get("matricula");
    }
}
