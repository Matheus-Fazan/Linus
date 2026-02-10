package com.linus.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoUtil {

    /**
     * Método que fecha recursos de operações JDBC.
     *
     * @param ps objeto {@code PreparedStatement} a ser fechado
     * @param rs objeto {@code ResultSet} a ser fechado
     * @throws SQLException caso ocorra erro no fechamento
     */
    public static void closeResources(PreparedStatement ps, ResultSet rs) throws SQLException {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
    }

    /**
     * Método que fecha recursos de operações JDBC.
     *
     * @param st objeto {@code Statement} a ser fechado
     * @param rs objeto {@code ResultSet} a ser fechado
     * @throws SQLException caso ocorra erro no fechamento
     */
    public static void closeResources(Statement st, ResultSet rs) throws SQLException {
        if (rs != null) rs.close();
        if (st != null) st.close();
    }

    /**
     * Método que fecha recursos de operações JDBC.
     *
     * @param ps objeto {@code PreparedStatement} a ser fechado
     * @throws SQLException caso ocorra erro no fechamento
     */
    public static void closeResources(PreparedStatement ps) throws SQLException {
        if (ps != null) ps.close();
    }
}
