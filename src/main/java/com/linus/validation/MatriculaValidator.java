package com.linus.validation;

import com.linus.exception.requestParam.EmptyParamException;
import com.linus.exception.requestParam.OutOfBoundsParamException;
import com.linus.exception.requestParam.OutOfPatternMatriculaException;
import com.linus.exception.requestParam.ParamException;

import static com.linus.utils.ValidationUtil.*;

public class MatriculaValidator implements ParamValidator {

    @Override
    public void validate(String value) throws ParamException {
        if (isEmptyString(value)) throw new EmptyParamException("matricula");
        if (isOutOfBoundString(value)) throw new OutOfBoundsParamException("matricula");
        if (isNotParseableToLong(value)) throw new OutOfPatternMatriculaException();
    }
}
