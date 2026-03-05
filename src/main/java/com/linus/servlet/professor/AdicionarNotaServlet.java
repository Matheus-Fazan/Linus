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

@WebServlet("/professor/adicionar-nota")
public class AdicionarNotaServlet extends HttpServlet {

    private static final NotaDao dao = new NotaDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/professor/adicionarNota.jsp").forward(req, resp);
    }
}
