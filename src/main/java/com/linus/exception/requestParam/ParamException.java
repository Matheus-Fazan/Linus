package com.linus.exception.requestParam;

/**
 * Classe de excessão genérica de erro de validação nos parâmetros de requisição
 */
public sealed class ParamException extends RuntimeException permits EmptyParamException, OutOfBoundsParamException, OutOfPatternCpfException, OutOfPatternEmailException, OutOfPatternPasswordException
{

    public ParamException(String message) {
        super(message);
    }
}
