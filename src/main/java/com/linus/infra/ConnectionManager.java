package com.linus.infra;

import com.linus.exception.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que encapsula geração de objetos {@code Connection} <br>
 * para implementações da classe {@code GenericDaoInterface}
 */
public class ConnectionManager {

    /**
     * Estabelece uma conexão com o banco de dados PostgreSQL.
     *
     * @return objeto {@code Connection} configurado com as credenciais do ambiente
     * @throws ConnectionException se houver algum erro de conexão.
     */
    public static Connection connect() throws ConnectionException {
        try{
            loadDriverClass();

            Map<String, String> env = getEnvironmentVariables();

            Connection con = getConnection(env);

            return con;
        } catch (Exception cause) {
            throw new ConnectionException(cause);
        }

    }

    /**
     * Carrega a classe do driver JDBC do PostgreSQL.
     *
     * @throws ClassNotFoundException se a classe do driver não estiver disponível no classpath
     */
    private static void loadDriverClass() throws ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
    }

    /**
     * Obtém as variáveis de ambiente necessárias para a conexão com o banco.
     *
     * @return {@code Map} contendo DB_URL, DB_USER e DB_PASSWORD do ambiente do sistema
     * @throws IllegalStateException se as variaveis de ambiente não estiverem configuradas
     */
    private static Map<String, String> getEnvironmentVariables() throws IllegalStateException {
        Map<String, String> envMap = new HashMap();

        String dbURL = System.getenv("DB_URL");
        String dbUSER = System.getenv("DB_USER");
        String dbPASSWORD = System.getenv("DB_PASSWORD");

        if (dbURL == null || dbUSER == null || dbPASSWORD == null) {
            throw new IllegalStateException(
                    "Variáveis de ambiente obrigatórias não configuradas: DB_URL, DB_USER, DB_PASSWORD"
            );
        }

        envMap.put("DB_URL", dbURL);
        envMap.put("DB_USER", dbUSER);
        envMap.put("DB_PASSWORD", dbPASSWORD);

        return envMap;
    }

    /**
     * Cria uma conexão JDBC usando as credenciais fornecidas.
     *
     * @param env mapa contendo DB_URL, DB_USER e DB_PASSWORD
     * @return objeto {@code Connection} conectado ao banco de dados
     * @throws SQLException se houver erro ao estabelecer a conexão
     */
    private static Connection getConnection(Map<String, String> env) throws SQLException {
        return DriverManager.getConnection(env.get("DB_URL"), env.get("DB_USER"), env.get("DB_PASSWORD"));
    }
}