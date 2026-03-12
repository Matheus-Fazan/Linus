package com.linus.servlet.professor;

import com.linus.dao.ProfessorDao;
import com.linus.dto.ProfessorPerfilDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.model.servlet.RequestReponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/professor/perfil")
public class ProfessorPerfilServlet extends HttpServlet {

    private static final ProfessorDao dao = new ProfessorDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        try {
            Number idSession = (Number) requestReponse.getSessionAttribute("idUsuario");
            long idProfessor = idSession.longValue();

            ProfessorPerfilDto dto = dao.findById(idProfessor);

            if (dto == null) {
                requestReponse.addRequestAttribute("error", "Professor não encontrado.");
            } else {
                requestReponse.addRequestAttribute("perfil", dto);
            }

        } catch (SQLException | ConnectionException cause) {
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, tente novamente.");

        } finally {
            requestReponse.forwardTo("/WEB-INF/pages/professor/perfil.jsp");
        }
    }
}