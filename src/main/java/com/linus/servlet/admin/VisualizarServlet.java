package com.linus.servlet.admin;

import com.linus.dao.AlunoDao;
import com.linus.dao.ProfessorDao;
import com.linus.dto.AlunoVisualizarDto;
import com.linus.dto.ProfessorVisualizarDto;
import com.linus.model.servlet.RequestReponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/visualizar")
public class VisualizarServlet extends HttpServlet {

    private static final AlunoDao alunoDao = new AlunoDao();
    private static final ProfessorDao professorDao = new ProfessorDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        try {
            List<AlunoVisualizarDto> alunos = alunoDao.findAllForVisualizacao();
            List<ProfessorVisualizarDto> professores = professorDao.findAllForVisualizacao();

            requestReponse.addRequestAttribute("alunos", alunos);
            requestReponse.addRequestAttribute("professores", professores);
            requestReponse.addRequestAttribute("encontrado", !alunos.isEmpty() || !professores.isEmpty());

        } catch (Exception cause) {
            cause.printStackTrace();
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor: " + cause.getMessage());

        } finally {
            requestReponse.forwardTo("/WEB-INF/pages/admin/visualizar.jsp");
        }
    }
}
