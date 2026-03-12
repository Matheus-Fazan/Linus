package com.linus.servlet.admin;

import com.linus.dao.AlunoDao;
import com.linus.dto.AlunoDto;
import com.linus.dto.AlunoPerfilDto;
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

@WebServlet("/admin/alterar-email-aluno")
public class AlterarEmailAlunoServlet extends HttpServlet {

    private static final AlunoDao dao = new AlunoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        try {
            String matricula = requestReponse.getRequestParameter("matricula");

            if (matricula != null && !matricula.isEmpty()) {
                AlunoPerfilDto dto = dao.findByMatricula(Long.parseLong(matricula));

                if (dto == null) {
                    requestReponse.addRequestAttribute("error", "Aluno não encontrado.");
                    requestReponse.addRequestAttribute("encontrado", false);
                } else {
                    requestReponse.addRequestAttribute("aluno", dto);
                    requestReponse.addRequestAttribute("encontrado", true);
                }
            }

        } catch (Exception cause) {
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, tente novamente.");
            requestReponse.addRequestAttribute("encontrado", false);

        } finally {
            requestReponse.forwardTo("/WEB-INF/pages/admin/editarAluno.jsp");
        }
    }

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
            doGet(req, resp);
        }
    }
}




