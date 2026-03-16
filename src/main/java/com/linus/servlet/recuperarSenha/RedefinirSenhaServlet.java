package com.linus.servlet.recuperarSenha;

import com.linus.dao.AcessoDao;
import com.linus.dao.AlunoDao;
import com.linus.dao.ProfessorDao;
import com.linus.exception.dao.DataAccessException;
import com.linus.exception.dao.EntityNotFoundException;
import com.linus.utils.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/redefinir-senha")
public class RedefinirSenhaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Boolean ehSenhaVerificada = (Boolean) session.getAttribute("ehSenhaRedefinidaVerificada");
        String email = (String) session.getAttribute("emailParaRecuperacao");

        if (ehSenhaVerificada == null || !ehSenhaVerificada || email == null) {
            resp.sendRedirect("index.jsp");
            return;
        }

        String novaSenha = req.getParameter("password");
        String confirmarSenha = req.getParameter("passwordConfirm");

        if (novaSenha == null || novaSenha.isBlank() || !novaSenha.equals(confirmarSenha)) {
            req.setAttribute("error", "As senhas não coincidem ou estão vazias.");
            req.getRequestDispatcher("/WEB-INF/pages/recuperarSenha/cadastroSenha.jsp").forward(req, resp);
            return;
        }

        if (ValidationUtil.isNotValidPassword(novaSenha)) {
            req.setAttribute("error", """
                    A senha deve ter pelo menos 8 caracteres, incluindo:
                    
                    1 letra minúscula
                    1 letra maiúscula
                    1 número
                    1 caractere especial (ex: !@#$%^&*).
                    
                    """);
            req.getRequestDispatcher("/WEB-INF/pages/recuperarSenha/cadastroSenha.jsp").forward(req, resp);
            return;
        }

        try {
            AcessoDao acessoDAO = new AcessoDao();

            Optional<String[]> resultado = acessoDAO.findCargoByEmail(email);

            if (resultado.isEmpty()) throw new EntityNotFoundException(email);

            String idOrigem = resultado.get()[0];
            String cargo    = resultado.get()[1];

            switch (cargo) {
                case "aluno" -> new AlunoDao().alterarSenhaPorId(Long.parseLong(idOrigem), novaSenha);
                case "professor" -> new ProfessorDao().alterarSenhaPorId(Long.parseLong(idOrigem), novaSenha);
                default -> throw new EntityNotFoundException("Cargo não reconhecido: " + cargo);
            }

            session.invalidate();
            req.getSession().setAttribute("successMessage", "Senha redefinida com sucesso! Faça login.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);

        } catch (EntityNotFoundException enfe) {
            req.setAttribute("error", enfe.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/recuperarSenha/cadastroSenha.jsp").forward(req, resp);
        } catch (DataAccessException dae) {
            System.err.println("[ERRO INTERNO]: " + dae);
            req.setAttribute("error", "Erro ao acessar os dados. Tente novamente mais tarde.");
            req.getRequestDispatcher("/WEB-INF/pages/erro.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Erro ao redefinir a senha. Tente novamente mais tarde.");
            req.getRequestDispatcher("/WEB-INF/pages/recuperarSenha/cadastroSenha.jsp").forward(req, resp);
        }
    }
}