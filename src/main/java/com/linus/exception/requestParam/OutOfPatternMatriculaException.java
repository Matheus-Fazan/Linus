package com.linus.exception.requestParam;

public final class OutOfPatternMatriculaException extends ParamException {

    private static final String MESSAGE = "Campo de 'matricula' conter apenas números. Por favor, insira um valor válido.";

    public OutOfPatternMatriculaException() {
        super(MESSAGE);
    }
}
