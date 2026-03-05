package com.linus.servlet.auth;

import com.linus.dao.AcessoDAO;
import com.linus.model.dao.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException, IOException {
        String email = requisicao.getParameter("email");
        String senha = requisicao.getParameter("senha");

        if (email == null || email.trim().isEmpty() ||
                senha == null || senha.trim().isEmpty()) {
            resposta.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            requisicao.setAttribute("error", "Email e senha são obrigatórios");
            requisicao.getRequestDispatcher("/index.jsp").forward(requisicao, resposta);
            return;
        }

        try {
            AcessoDAO acessoDAO = new AcessoDAO();
            Usuario usuario = acessoDAO.auth(email, senha);
            System.out.println(usuario);
            if (usuario == null) {
                resposta.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                requisicao.setAttribute("error", "Email e/ou senha inválidos");
                requisicao.getRequestDispatcher("/index.jsp").forward(requisicao, resposta);
                return;
            }

            HttpSession sessao = requisicao.getSession(true);

            sessao.setAttribute("usuarioLogado", usuario);
            sessao.setAttribute("perfilUsuario", usuario.getCargo());
            sessao.setAttribute("nomeUsuario", usuario.getNome());
            sessao.setAttribute("idUsuario", usuario.getId());

            String urlRedirecionamento = switch (usuario.getCargo().toLowerCase()) {
                case "admin"     -> "/admin/dashboard";
                case "professor" -> "/professor/notas";
                case "aluno"     -> "/aluno/boletim";
                default          -> "/index.jsp";
            };

            resposta.sendRedirect(requisicao.getContextPath() + urlRedirecionamento);

        } catch (Exception e) {
            e.printStackTrace();
            resposta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            requisicao.setAttribute("error", "Ocorreu um erro durante a autenticação. Tente novamente.");
            requisicao.getRequestDispatcher("/index.jsp").forward(requisicao, resposta);
        }
    }
}