package com.linus.servlet.auth;

import com.linus.model.servlet.RequestReponse;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final List<String> ROTAS_PUBLICAS_EXATAS = Arrays.asList(
            "/",
            "/logout",
            "/index.jsp",
            "/login",
            "/acesso-negado.jsp",
            "/recuperar-senha",
            "/verificar-codigo",
            "/redefinir-senha",
            "/primeiro-acesso.jsp",
            "/aluno/primeiro-acesso"
    );

    @Override
    public void doFilter(ServletRequest requisicao, ServletResponse resposta, FilterChain cadeia)
            throws IOException, ServletException {

        HttpServletRequest reqHttp = (HttpServletRequest) requisicao;
        HttpServletResponse respHttp = (HttpServletResponse) resposta;

        RequestReponse utilitarioReqResp = new RequestReponse(reqHttp, respHttp);
        HttpSession sessao = reqHttp.getSession(false);

        String caminho = reqHttp.getRequestURI().substring(reqHttp.getContextPath().length());

        if (ehRotaPublica(caminho)) {
            cadeia.doFilter(requisicao, resposta);
            return;
        }

        if (sessao == null || utilitarioReqResp.getSessionAttribute("usuarioLogado") == null) {
            utilitarioReqResp.redirectTo("/index.jsp");
            return;
        }

        String perfilUsuario = (String) utilitarioReqResp.getSessionAttribute("perfilUsuario");

        if (!ehAutorizado(perfilUsuario, caminho)) {
            utilitarioReqResp.redirectTo("/acesso-negado.jsp");
            return;
        }

        cadeia.doFilter(requisicao, resposta);
    }

    private boolean ehAutorizado(String perfilUsuario, String caminho) {
        if (perfilUsuario == null) return false;

        return switch (perfilUsuario.toLowerCase()) {
            case "professor" -> caminho.startsWith("/professor/") || caminho.startsWith("/aluno/boletim/");
            case "aluno"     -> caminho.startsWith("/aluno/");
            case "admin"     -> true;
            default          -> false;
        };
    }

    private boolean ehRotaPublica(String caminho) {
        if (ROTAS_PUBLICAS_EXATAS.contains(caminho)) {
            return true;
        }

        return caminho.startsWith("/assets/");
    }

    @Override
    public void init(FilterConfig config) {}
}