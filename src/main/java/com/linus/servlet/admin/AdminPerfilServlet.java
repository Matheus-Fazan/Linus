package com.linus.servlet.admin;

import com.linus.dao.AdminDao;
import com.linus.dto.AdminDto;
import com.linus.dto.AdminPerfilDto;
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

@WebServlet("/admin/perfil")
public class AdminPerfilServlet extends HttpServlet {

    private static final AdminDao dao = new AdminDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        Map<String, String> requestParams = requestReponse.getAllRequestParameters();

        try {
            Validator.validateParams(requestParams);

            Long idAdmin = (Long) requestReponse.getSessionAttribute("idUsuario");
            AdminDto dto = new AdminDto();

            dto.id = String.valueOf(idAdmin);

            dto.email = requestParams.get("email");

            dao.updateEmail(dto);

            requestReponse.addRequestAttribute("success", "Email atualizado com sucesso!");

        } catch (ParamException cause) {
            requestReponse.addRequestAttribute("error", cause.getMessage());
        } catch (SQLException | ConnectionException cause) {
            requestReponse.addRequestAttribute("error", "Falha ao atualizar o email. Por favor, tente novamente.");
        } catch (NoRegistersAlteredException cause) {
            requestReponse.addRequestAttribute("error", "Nenhum registro foi alterado.");
        } finally {
            doGet(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        try {
            Long idAdmin = (Long) requestReponse.getSessionAttribute("idUsuario");

            AdminPerfilDto dto = dao.findById(idAdmin);

            if (dto == null) {
                requestReponse.addRequestAttribute("error", "Admin não encontrado.");
            } else {
                requestReponse.addRequestAttribute("perfil", dto);
            }

        } catch (Exception cause) {
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, tente novamente.");

        } finally {
            requestReponse.forwardTo("/WEB-INF/pages/admin/perfil.jsp");
        }
    }
}
