package com.linus.exception.dao;

public class DataAccessException extends RuntimeException {

    private class Util{
        private static final String MEG_DATA_ACCESS_EXCEPTION =
                """
                DataAccessException: Ocorreu um erro inesperado durante a operação de acesso a dados.
                """;

        private static String getMessege(){
            return MEG_DATA_ACCESS_EXCEPTION;
        }
    }
}
