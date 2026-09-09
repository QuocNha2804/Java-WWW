package com.fit.tuan2va3.web;

import com.fit.tuan2va3.service.AuthenticationService;
import com.fit.tuan2va3.session.UserSession;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Inject
    private AuthenticationService authenticatioService;

    @Inject
    private UserSession userSession;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (userSession.isLoggedIn()) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password= req.getParameter("password");

        if (!authenticatioService.authenticate(username,password)){
            req.setAttribute("error"," username or password is not correct");
            req.setAttribute("username",username);

            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(
                    req, resp);
            return;
        }
        userSession.login(username);
        resp.sendRedirect(req.getContextPath() + "/products");

    }
}
