package com.linus.servlet.admin;

import com.linus.dao.AdminDao;
import com.linus.dto.AdminPerfilDto;
import com.linus.model.servlet.RequestReponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/perfil")
public class AdminPerfilServlet extends HttpServlet {

    private static final AdminDao dao = new AdminDao();

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
