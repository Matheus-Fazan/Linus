package com.linus.exception.requestParam;

public final class EmptyParamException extends ParamException {

    private static final String MESSAGE_TEMPLATE = "Campo de '?' não pode estar vazio. Por favor, insira um valor válido.";

    public EmptyParamException(String param) {
        super(MESSAGE_TEMPLATE.replace("?", param));
    }
}
