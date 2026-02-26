package com.linus.validation;

import com.linus.exception.requestParam.EmptyParamException;
import com.linus.exception.requestParam.OutOfBoundsParamException;
import com.linus.exception.requestParam.OutOfPatternEmailException;
import com.linus.exception.requestParam.ParamException;

import static com.linus.utils.ValidationUtil.isEmptyString;
import static com.linus.utils.ValidationUtil.isOutOfBoundString;

public class EmailValidator implements ParamValidator{

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @Override
    public void validate(String value) throws ParamException {
        if (isEmptyString(value)) throw new EmptyParamException("email");
        if (isOutOfBoundString(value)) throw new OutOfBoundsParamException("email");
        if (!value.matches(EMAIL_REGEX)) throw new OutOfPatternEmailException();
    }
}
