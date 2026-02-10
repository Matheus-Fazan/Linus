package com.linus.exception;

public class NoRegistersAlteredException extends RuntimeException {

    public NoRegistersAlteredException() {
        super(Util.getMessege());
    }

    private class Util{

        private static final String MEG_NO_REGISTERS_ALTERED =
                """
                NoRegistersAlteredException: Operação de Dao não alterou nenhum registro no banco de dados.
                """;

        private static String getMessege(){
            return MEG_NO_REGISTERS_ALTERED;
        }
    }
}
