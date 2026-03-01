package com.linus.model.enums;

public enum Situacao {
    APROVADO("Aprovado"),
    REPROVADO("Reprovado"),
    EM_PROCESSO("Em Processo");

    private String descricao;

    Situacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
