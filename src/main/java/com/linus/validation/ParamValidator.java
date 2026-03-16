package com.linus.validation;

import com.linus.exception.requestParam.ParamException;

public interface ParamValidator {

    void validate(String value) throws ParamException;
}
