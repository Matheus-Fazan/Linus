package com.linus.exception.requestParam;

public final class OutOfPatternCpfException extends ParamException{

    private static final String MESSAGE = "Campo de 'cpf' deve conter um CPF válido com apenas os números. Por favor, insira um valor válido.";

    public OutOfPatternCpfException() {
        super(MESSAGE);
    }
}
