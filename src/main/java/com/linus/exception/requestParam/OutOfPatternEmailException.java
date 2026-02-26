package com.linus.exception.requestParam;

public final class OutOfPatternEmailException extends ParamException {

    private static final String MESSAGE = "Campo de 'email' inválido. Por favor, insira um valor válido.";

    public OutOfPatternEmailException() {
        super(MESSAGE);
    }
}
