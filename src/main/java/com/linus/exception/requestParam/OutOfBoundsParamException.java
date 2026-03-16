package com.linus.exception.requestParam;

public final class OutOfBoundsParamException extends ParamException {

    private static final String MESSAGE_TEMPLATE = "Campo de '?' deve conter no máximo 255 caracteres. Por favor, insira um valor válido.";

    public OutOfBoundsParamException(String param) {
        super(MESSAGE_TEMPLATE.replace("?", param));
    }
}
