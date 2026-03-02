package com.linus.servlet.aluno;

import com.linus.dao.BoletimDao;
import com.linus.dto.BoletimDto;
import com.linus.dto.EstatisticasBoletimDto;
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

@WebServlet("/aluno/boletim")
public class BoletimServlet extends HttpServlet {

    private static final BoletimDao dao = new BoletimDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestReponse requestReponse = new RequestReponse(req, resp);

        Long idUsuario = (Long) requestReponse.getSessionAttribute("idUsuario");

        try {

            List<BoletimDto> boletim = dao.findBoletimById(idUsuario);
            EstatisticasBoletimDto estatisticasBoletim = new EstatisticasBoletimDto(boletim);

            requestReponse.addRequestAttribute("boletim", boletim);
            requestReponse.addRequestAttribute("estatisticasBoletim", estatisticasBoletim);

        } catch (SQLException | ConnectionException cause) {
            requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, <a href=\"" + req.getContextPath() + "/aluno/boletim\">tente novamente</a>.");
        } finally {
            requestReponse.forwardTo("/../pagPrincipal.jsp");
        }
    }
}
