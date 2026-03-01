package com.linus.dto;

import java.util.Map;

/**
 * Classe de DTO que abstrai os aluno
 */
public class AlunoDto {

    public String nome;
    public String email;
    public String senha;
    public String cpf;
    public String matricula;
    public String idTurma;
    public TurmaDto turmaDto;

    public AlunoDto(Map<String, String> requestParams) {
        this.nome = requestParams.get("nome");
        this.email = requestParams.get("email");
        this.senha = requestParams.get("senha");
        this.cpf = requestParams.get("cpf");
        this.matricula = requestParams.get("matricula");
        this.idTurma = requestParams.get("id_turma");
    }
}
