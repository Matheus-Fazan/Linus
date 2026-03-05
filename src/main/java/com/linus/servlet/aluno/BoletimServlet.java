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
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestReponse requestReponse = new RequestReponse(req, resp);

        Long idUsuario = ((Integer) requestReponse.getSessionAttribute("idUsuario")).longValue();
        System.out.println("idUsuario: " + idUsuario); // log temporário

        try {

            List<BoletimDto> boletim = dao.findBoletimById(idUsuario);
            EstatisticasBoletimDto estatisticasBoletim = new EstatisticasBoletimDto(boletim);

            requestReponse.addRequestAttribute("boletim", boletim);
            requestReponse.addRequestAttribute("estatisticasBoletim", estatisticasBoletim);

        }catch (Exception cause) {
        cause.printStackTrace(); // isso PRECISA estar aqui

        //} catch (SQLException | ConnectionException cause) {
        //    requestReponse.addRequestAttribute("error", "Falha ao consultar o servidor. Por favor, <a href=\"" + req.getContextPath() + "/aluno/boletim\">tente novamente</a>.");
        //
        }finally {
            requestReponse.forwardTo("/WEB-INF/pages/aluno/pagPrincipal.jsp");
        }
    }
}
