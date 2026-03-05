package com.linus.validation;

import com.linus.exception.requestParam.EmptyParamException;
import com.linus.exception.requestParam.OutOfBoundsParamException;
import com.linus.exception.requestParam.ParamException;

import static com.linus.utils.ValidationUtil.isEmptyString;
import static com.linus.utils.ValidationUtil.isOutOfBoundString;

public class UsuarioValidator implements ParamValidator{

    @Override
    public void validate(String value) throws ParamException {
        if (isEmptyString(value)) throw new EmptyParamException("usuario");
        if (isOutOfBoundString(value)) throw new OutOfBoundsParamException("usuario");
    }
}
