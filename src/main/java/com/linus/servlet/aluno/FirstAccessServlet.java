package com.linus.servlet.aluno;

import com.linus.dao.AlunoDao;
import com.linus.dto.FirstAccessParametersDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.exception.dao.NoRegistersAlteredException;
import com.linus.exception.requestParam.ParamException;
import com.linus.model.servlet.RequestReponse;
import com.linus.validation.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/aluno/primeiro-acesso")
public class FirstAccessServlet extends HttpServlet {

    private static final AlunoDao dao = new AlunoDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        Map<String, String> requestParams = requestReponse.getAllRequestParameters();

        try {
            Validator.validateParams(requestParams);

            FirstAccessParametersDto dto = new FirstAccessParametersDto(requestParams);

            dao.updateWithoutIdTurma(dto);

            requestReponse.addRequestAttribute("success", "Cadastro realizado com sucesso! Agora você pode realizar o seu login na tela de inicio!");
            requestReponse.forwardTo("/escola/primeiro-acesso.jsp");

        } catch (ParamException cause) {
            requestReponse.addRequestAttribute("error", cause.getMessage());
            requestReponse.forwardTo("/escola/primeiro-acesso.jsp");

        } catch (SQLException | ConnectionException cause) {
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, tente novamente.");
            requestReponse.forwardTo("/escola/primeiro-acesso.jsp");

        } catch (NoRegistersAlteredException cause) {
            requestReponse.addRequestAttribute("error", "Matricula não encontrada. Por favor, contate a escola para realizar o seu pré-cadastro.");
            requestReponse.forwardTo("/escola/primeiro-acesso.jsp");

        }

    }
}
