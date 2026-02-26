package com.linus.validation;

import br.com.caelum.stella.validation.CPFValidator;
import com.linus.exception.requestParam.EmptyParamException;
import com.linus.exception.requestParam.OutOfBoundsParamException;
import com.linus.exception.requestParam.OutOfPatternCpfException;
import com.linus.exception.requestParam.ParamException;

import static com.linus.utils.ValidationUtil.isEmptyString;
import static com.linus.utils.ValidationUtil.isOutOfBoundString;

public class CpfValidator implements ParamValidator {

    private static final CPFValidator CPF_VALIDATOR = new CPFValidator();

    @Override
    public void validate(String value) throws ParamException {
        if (isEmptyString(value)) throw new EmptyParamException("cpf");
        if (isOutOfBoundString(value)) throw new OutOfBoundsParamException("cpf");

        try {
            CPF_VALIDATOR.assertValid(value);
        } catch (Exception e) {
            throw new OutOfPatternCpfException();
        }
    }
}
