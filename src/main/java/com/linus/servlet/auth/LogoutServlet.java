package com.linus.servlet.auth;

import com.linus.model.servlet.RequestReponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        resp.setHeader("Allow", "POST");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestReponse requestReponse = new RequestReponse(req, resp);
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        requestReponse.redirectTo("/index.jsp");
    }
}