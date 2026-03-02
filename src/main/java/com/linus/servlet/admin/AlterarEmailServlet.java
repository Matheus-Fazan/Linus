package com.linus.servlet.admin;

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

@WebServlet("/admin/alterar-email")
public class AlterarEmailServlet extends HttpServlet {

    private static final AlunoDao dao = new AlunoDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestReponse requestReponse = new RequestReponse(req, resp);

        Map<String, String> requestParms = requestReponse.getAllRequestParameters();

        try {
            Validator.validateParams(requestParms);

            AlunoDto dto = new AlunoDto(requestParms);

            dao.updateEmailByMatricula(dto);

            requestReponse.addRequestAttribute("success", "Email alterado com sucesso na matricula:" + dto.matricula + ".");
        } catch (ParamException cause) {
            requestReponse.addRequestAttribute("error", cause.getMessage());

        } catch (SQLException | ConnectionException cause) {
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, tente novamente.");

        } catch (NoRegistersAlteredException cause) {
            requestReponse.addRequestAttribute("error", "Nenhum registro foi alterado. Por favor, verifique a matrícula informada.");

        } finally {
            requestReponse.forwardTo("/../editarAluno.jsp");
        }
    }
}




