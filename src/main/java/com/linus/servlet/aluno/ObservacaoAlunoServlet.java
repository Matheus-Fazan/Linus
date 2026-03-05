package com.linus.servlet.aluno;

import com.linus.dao.ObservacaoDao;
import com.linus.exception.dao.ConnectionException;
import com.linus.model.dao.Observacao;
import com.linus.model.servlet.RequestReponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/aluno/observacoes")
public class ObservacaoAlunoServlet extends HttpServlet {

    private static final ObservacaoDao dao  = new ObservacaoDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestReponse requestReponse = new RequestReponse(req, resp);

        Long idUsuario = (Long) requestReponse.getSessionAttribute("idUsuario");

        try {
            List<Observacao> observacoes = dao.findAllById(idUsuario);

            requestReponse.addRequestAttribute("observacoes", observacoes);
        } catch (SQLException | ConnectionException cause) {
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, <a href=\"" + req.getContextPath() + "/aluno/observacoes\">tente novamente</a>.");
        } finally {
            requestReponse.forwardTo("/WEB-INF/pages/aluno/observacaoAluno.jsp");
        }
    }
}
