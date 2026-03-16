package com.linus.model.dao;

import lombok.Data;

@Data
public class Usuario {
    private Long    id;
    private String nome;
    private String email;
    private String cargo;
    private String tipoTabela;

    public Usuario() {}

    public Usuario(Long id, String nome, String email,
                           String cargo, String tipoTabela) {
        this.id         = id;
        this.nome       = nome;
        this.email      = email;
        this.cargo      = cargo;
        this.tipoTabela = tipoTabela;
    }
}
