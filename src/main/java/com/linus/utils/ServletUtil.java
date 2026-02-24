package com.linus.utils;

import org.mindrot.jbcrypt.BCrypt;

public class ServletUtil {

    /**
     * Metodo utilitario que gera hash {@code BCrypt} a partir de um objeto {@code String}
     *
     * @param password objeto {@code String} a ser hasheado
     * @return um objeto {@code String} hashado [ROUNDS: 12][SALT: ALEATORIO]
     */
    public static String toBCryptHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}
