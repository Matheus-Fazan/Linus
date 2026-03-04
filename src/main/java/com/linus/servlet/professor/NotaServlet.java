package com.linus.servlet.professor;

import com.linus.dao.NotaDao;
import com.linus.dto.NotaDto;
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

@WebServlet("/professor/notas")
public class NotaServlet extends HttpServlet {

    private static final NotaDao dao = new NotaDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/professor/tabelaNotas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestReponse requestReponse = new RequestReponse(req, resp);

        Map<String, String> requestParms = requestReponse.getAllRequestParameters();

        try {
            Validator.validateParams(requestParms);

            NotaDto dto = new NotaDto(requestParms);

            String professorMatricula = (String) req.getSession().getAttribute("matricula");

            dto.idProfessor = professorMatricula;

            dao.updateNotaByProfessor(dto);

            requestReponse.addRequestAttribute("success", "Nota alterada com sucesso!");
        } catch (ParamException cause) {
            requestReponse.addRequestAttribute("error", cause.getMessage());

        } catch (SQLException | ConnectionException cause) {
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, tente novamente.");

        } catch (NoRegistersAlteredException cause) {
            requestReponse.addRequestAttribute("error", "Falha ao alterar nota. Verifique se você tem permissão para alterar esta nota.");

        } finally {
            requestReponse.forwardTo("/WEB-INF/pages/professor/tabelaNotas.jsp");
        }
    }
}