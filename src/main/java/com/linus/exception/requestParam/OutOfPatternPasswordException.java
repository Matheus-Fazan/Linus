package com.linus.exception.requestParam;

public final class OutOfPatternPasswordException extends ParamException{

    private static final String MESSAGE = "Campo de 'senha' deve conter no mínimo 8 caracteres e ao menos um carácter maiúsculo e numérico. Por favor, insira um valor válido.";

    public OutOfPatternPasswordException() {
        super(MESSAGE);
    }
}
