package com.linus.servlet.aluno;

import com.linus.dao.AlunoDao;
import com.linus.dto.AlunoPerfilDto;

import com.linus.exception.dao.ConnectionException;
import com.linus.model.servlet.RequestReponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/aluno/perfil")
public class AlunoPerfilServlet extends HttpServlet {

    private static final AlunoDao dao = new AlunoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        try {
            Integer matricula = (Integer) requestReponse.getSessionAttribute("idUsuario");

            AlunoPerfilDto dto = dao.findByMatricula(matricula);

            if (dto == null) {
                requestReponse.addRequestAttribute("error", "Aluno não encontrado.");
            } else {
                requestReponse.addRequestAttribute("perfil", dto);
            }

        } catch (SQLException | ConnectionException cause) {
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, tente novamente.");

        } finally {
            requestReponse.forwardTo("/WEB-INF/pages/aluno/perfil.jsp");
        }
    }
}