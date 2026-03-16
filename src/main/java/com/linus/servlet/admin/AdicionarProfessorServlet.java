package com.linus.servlet.admin;

import com.linus.dao.ProfessorDao;
import com.linus.dto.ProfessorDto;
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

@WebServlet("/admin/adicionar-professor")
public class AdicionarProfessorServlet extends HttpServlet {

    private final static ProfessorDao dao = new ProfessorDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        Map<String, String> requestParms = requestReponse.getAllRequestParameters();

        try {
            Validator.validateParams(requestParms);

            ProfessorDto dto = new ProfessorDto(requestParms);

            String generatedId = dao.saveReturningId(dto);

            req.getSession().setAttribute("successMessage", "Cadastro realizado com sucesso!");
        } catch (ParamException cause) {
            req.getSession().setAttribute("errorMessage", cause.getMessage());

        } catch (SQLException | ConnectionException cause) {
            req.getSession().setAttribute("errorMessage", "Falha ao consultar o servidor. Por favor, tente novamente.");

        } catch (NoRegistersAlteredException cause) {
            req.getSession().setAttribute("errorMessage", "Falha ao adicionar. Por favor, tente novamente.");

        } finally {
            resp.sendRedirect(req.getContextPath() + "/admin/criar-acesso");
        }
    }
}
