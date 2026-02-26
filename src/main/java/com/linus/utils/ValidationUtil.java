package com.linus.utils;

public class ValidationUtil {

    private static String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{}|;':\",./<>?])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{}|;':\",./<>?]{8,}$";

    /**
     * Metodo que valida se um objeto {@code String} está vazio ou eh null
     * @param s objeto {@code String} a ser validado
     * @return um boleano
     */
    public static boolean isEmptyString(String s) {
        return (s == null || s.trim().equals(""));
    }

    /**
     * Metodo que valida se um objeto {@code String} é maior que uma quantidade maxima de caracteres
     * @param s objeto {@code String} a ser validado
     * @return um boleano
     */
    public static boolean isOutOfBoundString(String s){
        return (s.length() > 255);
    }

    /**
     *  Metodo que valida se um objeto {@code String} não atende ao padrão de email
     * @param s objeto {@code String} a ser validado
     * @return um boleano
     */
    public static boolean isNotValidEmail(String s) {
        return !s.matches(REGEX_EMAIL);
    }

    /**
     *  Metodo que valida se um objeto {@code String} não atende ao padrão de senha
     * @param s objeto {@code String} a ser validado
     * @return um boleano
     */
    public static boolean isNotValidPassword(String s) {
        return !s.matches(REGEX_PASSWORD);
    }

    /**
     * Metodo que valida se um objeto {@code String}
     * @param s objeto {@code String} a ser validado
     * @return um boleano
     */
    public static boolean isNotParseableToLong(String s) {
        boolean parseable = false;

        try {
            Long.parseLong(s);
            parseable = true;
        } catch (NumberFormatException e) {}

        return !parseable;
    }
}
