package com.linus.servlet.admin;

import com.linus.dao.AlunoDao;
import com.linus.dto.AlunoDto;
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

@WebServlet("/admin/adicionar-aluno")
public class AdicionarAlunoServlet extends HttpServlet {

    private static final AlunoDao dao = new AlunoDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestReponse requestReponse = new RequestReponse(req, resp);

        Map<String, String> requestParms = requestReponse.getAllRequestParameters();

        try {
            Validator.validateParams(requestParms);

            AlunoDto dto = new AlunoDto(requestParms);

            String generatedMatricula = dao.previousSave(dto);

            req.getSession().setAttribute("successMessage", "Pré-cadastro realizado com sucesso!");
            req.getSession().setAttribute("matricula", generatedMatricula);
        } catch (ParamException cause) {
            req.getSession().setAttribute("errorMessage", cause.getMessage());

        } catch (SQLException | ConnectionException cause) {
            req.getSession().setAttribute("errorMessage", "Falha ao consultar o servidor. Por favor, tente novamente.");

        } catch (NoRegistersAlteredException cause) {
            req.getSession().setAttribute("errorMessage", "Falha ao adicionar pré-cadastro. Por favor, tente novamente.");

        } finally {
            resp.sendRedirect(req.getContextPath() + "/admin/criar-acesso");
        }
    }
}
