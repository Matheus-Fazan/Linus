package com.linus.exception;

public class ConnectionException extends RuntimeException {

    public ConnectionException(Throwable cause) {
        super(Util.getErrorMessege(cause), cause);
    }

    private class Util{

        private static final String MSG_ERRO_DRIVER =
                """
                ConnectionException: Driver Postgresql não encontrado.
                """;

        private static final String MSG_ERRO_SQL =
                """
                ConnectionException: Erro ao estabelecer a conexão com o banco de dados.
                """;

        private static final String MSG_ERRO_ENV =
                """
                ConnectionException: Variáveis de ambiente de acesso a base de dados não configuradas.
                """;

        private static String getErrorMessege(Throwable cause){
            String cause_class_name = cause.getClass().getSimpleName();

            return switch (cause_class_name){
                case "ClassNotFoundException" -> MSG_ERRO_DRIVER;
                case "SQLException" -> MSG_ERRO_SQL;
                default -> MSG_ERRO_ENV;
            };
        }
    }
}
