package com.linus.servlet.aluno;

import com.linus.dao.AlunoDao;
import com.linus.dto.AlunoDto;
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
public class PrimeiroAcessoServlet extends HttpServlet {

    private static final AlunoDao dao = new AlunoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);
        requestReponse.forwardTo("/primeiro-acesso.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestReponse requestReponse = new RequestReponse(req, resp);

        Map<String, String> requestParms =  requestReponse.getAllRequestParameters();

        try {

            Validator.validateParams(requestParms);

            dao.firstAccess(new AlunoDto(requestParms));

            requestReponse.addRequestAttribute("sucess", "Primeiro acesso realizado com sucesso! Agora é possível fazer o login.");

        } catch (SQLException | ConnectionException cause) {
            cause.printStackTrace();
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, tente novamente.");

        } catch (NoRegistersAlteredException cause) {
            requestReponse.addRequestAttribute("error", "Pré cadastro não encontrado para matricula: " + requestParms.get("matricula"));

        } catch (ParamException cause) {
            requestReponse.addRequestAttribute("error", cause.getMessage());

        } finally {
            requestReponse.forwardTo("/primeiro-acesso.jsp");

        }
    }
}
