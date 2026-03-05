package com.linus.dto;

import java.util.ArrayList;
import java.util.List;

public class EstatisticasBoletimDto {

    public String notasBaixas;
    public String situacao;

    public EstatisticasBoletimDto(List<BoletimDto> boletim) {

        List<String> medias = new ArrayList<>();
        int notasBaixas = 0;
        String situacao = "Aprovado";

        for (BoletimDto nota : boletim) {
            if (!nota.n1.equals("-") && Double.valueOf(nota.n1) < 6) notasBaixas++;
            if (!nota.n2.equals("-") && Double.valueOf(nota.n2) < 6) notasBaixas++;
            medias.add(nota.media);
        }

        if (medias.contains("-")) {
            situacao = "No processo";
        } else {
            for (String media : medias) {
                if (Double.valueOf(media) < 6) {
                    situacao = "Reprovado";
                    break;
                }
            }
        }

        this.notasBaixas = String.valueOf(notasBaixas);
        this.situacao = situacao;
    }
}