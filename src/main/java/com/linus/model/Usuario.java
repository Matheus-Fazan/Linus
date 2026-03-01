package com.linus.model;

import lombok.Data;

@Data
public class Usuario {
    private int    id;
    private String nome;
    private String email;
    private String cargo;
    private String tipoTabela;

    public Usuario() {}

    public Usuario(int id, String nome, String email,
                           String cargo, String tipoTabela) {
        this.id         = id;
        this.nome       = nome;
        this.email      = email;
        this.cargo      = cargo;
        this.tipoTabela = tipoTabela;
    }
}
