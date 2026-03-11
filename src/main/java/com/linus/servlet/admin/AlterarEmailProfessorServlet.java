package com.linus.servlet.admin;

import com.linus.dao.ProfessorDao;
import com.linus.dto.ProfessorDto;
import com.linus.dto.ProfessorPerfilDto;
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

@WebServlet("/admin/alterar-email-professor")
public class AlterarEmailProfessorServlet extends HttpServlet {

    private static final ProfessorDao dao = new ProfessorDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        try {
            String idProfessor = requestReponse.getRequestParameter("id");

            if (idProfessor != null && !idProfessor.isEmpty()) {
                ProfessorPerfilDto dto = dao.findById(Long.parseLong(idProfessor));

                if (dto == null) {
                    requestReponse.addRequestAttribute("error", "Professor não encontrado.");
                } else {
                    requestReponse.addRequestAttribute("professor", dto);
                }
            }

        } catch (Exception cause) {
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, tente novamente.");

        } finally {
            requestReponse.forwardTo("/WEB-INF/pages/admin/editarProfessor.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestReponse requestReponse = new RequestReponse(req, resp);

        Map<String, String> requestParms = requestReponse.getAllRequestParameters();

        try {
            Validator.validateParams(requestParms);

            ProfessorDto dto = new ProfessorDto(requestParms);

            dao.updateEmailById(dto);

            requestReponse.addRequestAttribute("success", "Email alterado com sucesso para o professor ID:" + dto.id + ".");
        } catch (ParamException cause) {
            requestReponse.addRequestAttribute("error", cause.getMessage());

        } catch (SQLException | ConnectionException cause) {
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, tente novamente.");

        } catch (NoRegistersAlteredException cause) {
            requestReponse.addRequestAttribute("error", "Nenhum registro foi alterado. Por favor, verifique o ID do professor informado.");

        } finally {
            doGet(req, resp);
        }
    }
}
