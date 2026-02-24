package com.linus.dto;

/**
 * Classe de DTO que abstrai os parametros de requisicao para servlet {@code FirstAcessServlet}
 */
public class FirstAccessParametersDto {

    public String nome;
    public String email;
    public String hashSenha;
    public String cpf;
    public long matricula;

    public FirstAccessParametersDto(String nome, String email, String hashSenha, String cpf, long matricula) {
        this.nome = nome;
        this.email = email;
        this.hashSenha = hashSenha;
        this.cpf = cpf;
        this.matricula = matricula;
    }
}
