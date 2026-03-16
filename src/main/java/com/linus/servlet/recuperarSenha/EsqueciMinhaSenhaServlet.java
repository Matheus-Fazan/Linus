package com.linus.servlet.recuperarSenha;

import com.linus.exception.dao.EntityNotFoundException;
import com.linus.utils.EmailUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Random;

@WebServlet("/recuperar-senha")
public class EsqueciMinhaSenhaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String viewPath = "/WEB-INF/pages/recuperarSenha/esqueceuSenha.jsp";
        req.getRequestDispatcher(viewPath).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        String codigoVerificacao = String.valueOf(codigo);

        String email = req.getParameter("email");

        HttpSession session = req.getSession();
        session.setAttribute("codigoVerificacao", codigoVerificacao);
        session.setAttribute("emailParaRecuperacao", email);
        session.setAttribute("codigoExpiracao", System.currentTimeMillis());

        try {
            EmailUtil.enviarEmail(email, codigoVerificacao);
            System.out.println("Email de recuperação enviado para: " + email);

            req.getRequestDispatcher("/WEB-INF/pages/recuperarSenha/codigoVerificacao.jsp").forward(req, resp);
        } catch (MessagingException | UnsupportedEncodingException exception) {
            req.setAttribute("error", "Erro ao enviar o email. Tente novamente mais tarde!");
            req.getRequestDispatcher("/WEB-INF/pages/recuperarSenha/esqueceuSenha.jsp").forward(req, resp);
        } catch (EntityNotFoundException enfe) {
            req.setAttribute("error", enfe.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/recuperarSenha/esqueceuSenha.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}