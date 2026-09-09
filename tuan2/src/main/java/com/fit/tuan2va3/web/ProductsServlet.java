package com.fit.tuan2va3.web;

import com.fit.tuan2va3.service.ProductCatalog;
import com.fit.tuan2va3.session.UserSession;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
    @Inject

    private ProductCatalog productCatalog;
    @Inject
    private UserSession userSession;
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        if (!userSession.isLoggedIn()) {
            response.sendRedirect(request.getContextPath() +
                    "/login");
            return;
        }
        request.setAttribute("products", productCatalog.findAll());
        request.setAttribute("userSession", userSession);
        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);

    }
}
