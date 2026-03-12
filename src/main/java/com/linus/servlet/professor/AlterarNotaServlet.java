package com.linus.servlet.professor;

import com.linus.dao.NotaDao;
import com.linus.exception.dao.ConnectionException;
import com.linus.model.servlet.RequestReponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/professor/notas/alterar")
public class AlterarNotaServlet extends HttpServlet {

    private static final NotaDao dao = new NotaDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        try {
            long   idNota = Long.parseLong(requestReponse.getRequestParameter("idNota"));
            double n1     = Double.parseDouble(requestReponse.getRequestParameter("n1"));
            double n2     = Double.parseDouble(requestReponse.getRequestParameter("n2"));
            String observacao     = requestReponse.getRequestParameter("observacao");

            if (n1 < 0 || n1 > 10 || n2 < 0 || n2 > 10) {
                requestReponse.addSessionAttribute("error", "As notas devem estar entre 0 e 10.");
            } else {
                dao.update(idNota, n1, n2, observacao);
                requestReponse.addSessionAttribute("success", "Nota atualizada com sucesso!");
            }

        } catch (NumberFormatException cause) {
            requestReponse.addSessionAttribute("error", "Valores inválidos. Verifique as notas informadas.");

        } catch (SQLException | ConnectionException cause) {
            requestReponse.addSessionAttribute("error", "Falha ao salvar. Por favor, tente novamente.");

        } finally {
            requestReponse.redirectTo("/professor/inicio");
        }
    }
}