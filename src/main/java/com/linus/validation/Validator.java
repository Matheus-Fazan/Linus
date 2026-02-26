package com.linus.validation;

import com.linus.exception.requestParam.ParamException;

import java.util.Map;
import java.util.function.Consumer;

public class Validator {

    private static final Map<String, Consumer<String>> VALIDATORS = Map.of(
            "nome",      new NomeValidator()::validate,
            "email",     new EmailValidator()::validate,
            "senha",     new SenhaValidator()::validate,
            "cpf",       new CpfValidator()::validate,
            "matricula", new MatriculaValidator()::validate
    );

    public static void validateParams(Map<String, String> requestParams) throws ParamException {

        for (Map.Entry<String, String> entry : requestParams.entrySet()) {

            Consumer<String> validator = VALIDATORS.get(entry.getKey());

            if (validator != null) validator.accept(entry.getValue());

        }
    }
}
