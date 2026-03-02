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

            if ( !nota.n1.equals("-") ) {
                if ( Integer.valueOf(nota.n1) < 6) {
                    notasBaixas++;
                }
            }

            if ( !nota.n2.equals("-") ) {
                if ( Integer.valueOf(nota.n2) < 6) {
                    notasBaixas++;
                }
            }

            medias.add(nota.media);
        }

        if ( medias.contains("-") ) {
            situacao = "No processo";

            this.notasBaixas = String.valueOf(notasBaixas);
            this.situacao = situacao;

            return;
        }

        for (String media : medias) {

            if (Integer.valueOf(media) < 6) {
                situacao = "Reprovado";

                this.notasBaixas = String.valueOf(notasBaixas);
                this.situacao = situacao;

                return;
            }
        }
    }
}
