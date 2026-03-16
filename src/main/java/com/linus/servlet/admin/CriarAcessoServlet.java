package com.linus.servlet.admin;

import com.linus.dao.MateriaDao;
import com.linus.dao.TurmaDao;
import com.linus.model.dao.Materia;
import com.linus.model.dao.Turma;
import com.linus.model.servlet.RequestReponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/criar-acesso")
public class CriarAcessoServlet extends HttpServlet {

    private static final TurmaDao turmaDao = new TurmaDao();
    private static final MateriaDao materiaDao = new MateriaDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);

        try {
            List<Turma> turmas = turmaDao.findAll();
            List<Materia> materias = materiaDao.findAll();

            requestReponse.addRequestAttribute("turmas", turmas);
            requestReponse.addRequestAttribute("materias", materias);

        } catch (Exception cause) {
            requestReponse.addRequestAttribute("error", "Falha ao carregar dados. Por favor, tente novamente.");
        } finally {
            requestReponse.forwardTo("/WEB-INF/pages/admin/criarAcesso.jsp");
        }
    }
}
