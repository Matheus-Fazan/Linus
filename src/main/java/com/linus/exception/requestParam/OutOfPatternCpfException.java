package com.linus.exception.requestParam;

public final class OutOfPatternCpfException extends ParamException{

    private static String MESSAGE = "Campo de 'email' inválido. Por favor, insira um valor válido.";

    public OutOfPatternCpfException() {
        super(MESSAGE);
    }
}
