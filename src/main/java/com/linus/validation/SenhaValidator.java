package com.linus.validation;

import com.linus.exception.requestParam.EmptyParamException;
import com.linus.exception.requestParam.OutOfBoundsParamException;
import com.linus.exception.requestParam.OutOfPatternPasswordException;
import com.linus.exception.requestParam.ParamException;

import static com.linus.utils.ValidationUtil.isEmptyString;
import static com.linus.utils.ValidationUtil.isOutOfBoundString;

public class SenhaValidator implements ParamValidator{

    private static final String SENHA_REGEX = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$";

    @Override
    public void validate(String value) throws ParamException {
        if (isEmptyString(value)) throw new EmptyParamException("senha");
        if (isOutOfBoundString(value)) throw new OutOfBoundsParamException("senha");
        if (!value.matches(SENHA_REGEX)) throw new OutOfPatternPasswordException();
    }
}
