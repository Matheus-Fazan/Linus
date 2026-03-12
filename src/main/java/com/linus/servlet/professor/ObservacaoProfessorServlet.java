
package com.linus.servlet.professor;


import com.linus.dao.ObservacaoDao;
import com.linus.exception.dao.ConnectionException;
import com.linus.model.servlet.RequestReponse;
import com.linus.utils.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/professor/observacao/adicionar")
public class ObservacaoProfessorServlet extends HttpServlet {
    private static final ObservacaoDao dao = new ObservacaoDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        try {
            Number idProfessorSession = (Number) requestReponse.getSessionAttribute("idUsuario");
            long idProfessor = idProfessorSession.longValue();

            String matriculaParam = requestReponse.getRequestParameter("matricula");
            String observacao     = requestReponse.getRequestParameter("observacao");

            if (ValidationUtil.isEmptyString(matriculaParam)) {
                requestReponse.addSessionAttribute("error", "Matrícula não informada.");
                requestReponse.redirectTo("/professor/inicio");
                return;
            }

            if (ValidationUtil.isNotParseableToLong(matriculaParam)) {
                requestReponse.addSessionAttribute("error", "Matrícula inválida.");
                requestReponse.redirectTo("/professor/inicio");
                return;
            }

            if (ValidationUtil.isEmptyString(observacao)) {
                requestReponse.addSessionAttribute("error", "A observação não pode estar vazia.");
                requestReponse.redirectTo("/professor/inicio");
                return;
            }

            if (ValidationUtil.isOutOfBoundString(observacao)) {
                requestReponse.addSessionAttribute("error", "A observação não pode ultrapassar 255 caracteres.");
                requestReponse.redirectTo("/professor/inicio");
                return;
            }

            long matricula = Long.parseLong(matriculaParam);

            dao.save(idProfessor, matricula, observacao);

            requestReponse.addSessionAttribute("success", "Observação adicionada com sucesso!");

        } catch (SQLException | ConnectionException cause) {
            requestReponse.addSessionAttribute("error", "Falha ao salvar. Por favor, tente novamente.");

        } finally {
            requestReponse.redirectTo("/professor/inicio");
        }
    }
}
