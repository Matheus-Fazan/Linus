package com.linus.dto;

import java.util.Map;

/**
 * Classe de DTO que abstrai os dados de Admin
 */
public class AdminDto {

    public String id;
    public String email;
    public String senha;

    public AdminDto() {}

    public AdminDto(Map<String, String> requestParams) {
        this.id = requestParams.get("id");
        this.email = requestParams.get("email");
        this.senha = requestParams.get("senha");
    }
}