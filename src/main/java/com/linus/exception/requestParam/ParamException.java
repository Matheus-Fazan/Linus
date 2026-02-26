package com.linus.exception.requestParam;

public sealed class ParamException extends RuntimeException permits
        EmptyParamException,
        OutOfBoundsParamException,
        OutOfPatternCpfException,
        OutOfPatternEmailException,
        OutOfPatternMatriculaException,
        OutOfPatternPasswordException
{

    public ParamException(String message) {
        super(message);
    }
}
