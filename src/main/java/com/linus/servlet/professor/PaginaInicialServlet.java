package com.linus.servlet.professor;

import com.linus.dao.ObservacaoDao;
import com.linus.dao.ProfessorDao;
import com.linus.dto.AlunoProfessorDto;
import com.linus.dto.ObservacaoProfessorDto;
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

@WebServlet("/professor/inicio")
public class PaginaInicialServlet extends HttpServlet {

    private static final ProfessorDao dao = new ProfessorDao();
    private static final ObservacaoDao observacaoDao = new ObservacaoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        try {
            Number idProfessorSession = (Number) requestReponse.getSessionAttribute("idUsuario");
            long idProfessor = idProfessorSession.longValue();

            String matriculaParam = requestReponse.getRequestParameter("matricula");

            List<AlunoProfessorDto> notas;

            if (matriculaParam == null || matriculaParam.isBlank()) {
                notas = dao.findAlunosByProfessor(idProfessor);
            } else {
                long matricula = Long.parseLong(matriculaParam);
                notas = dao.findByMatriculaEProfessor(matricula, idProfessor);
            }

            if (notas.isEmpty()) {
                requestReponse.addRequestAttribute("encontrado", false);
            } else {
                requestReponse.addRequestAttribute("encontrado", true);
                requestReponse.addRequestAttribute("notas", notas);
            }

            List<ObservacaoProfessorDto> observacoes = observacaoDao.findByProfessor(idProfessor);
            requestReponse.addRequestAttribute("observacoes", observacoes);

        } catch (NumberFormatException cause) {
            requestReponse.addRequestAttribute("encontrado", false);
            requestReponse.addRequestAttribute("error", "Matrícula inválida.");

        } catch (SQLException | ConnectionException cause) {
            requestReponse.addRequestAttribute("encontrado", false);
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, tente novamente.");
            cause.printStackTrace();

        } finally {
            requestReponse.forwardTo("/WEB-INF/pages/professor/inicial.jsp");
        }
    }
}