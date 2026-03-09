package com.linus.servlet.professor;

import com.linus.dao.ProfessorDao;
import com.linus.dto.ListarAlunosDto;
import com.linus.exception.dao.ConnectionException;
import com.linus.model.servlet.RequestReponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/professor/alunos")
public class ListarAlunosServlet extends HttpServlet {

    private static final ProfessorDao dao = new ProfessorDao();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestReponse requestReponse = new RequestReponse(req, resp);

        Long idProfessor = (Long) requestReponse.getSessionAttribute("idUsuario");

        try {
            List<ListarAlunosDto> alunos = dao.findAlunosByProfessor(idProfessor);
            requestReponse.addRequestAttribute("alunos", alunos);

        } catch (SQLException | ConnectionException cause) {
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, <a href=\"" + req.getContextPath() + "/professor/alunos\">tente novamente</a>.");

        } finally {
            requestReponse.forwardTo("/WEB-INF/pages/professor/inicial.jsp");
        }
    }
}
