package com.library.servlet;

import com.library.dao.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        String role = userDAO.login(email, password);

        if (role == null) {
            response.getWriter().println("Login failed. <a href='login.jsp'>Try again</a>");
            return;
        }

        // Create the session -- this is the "wristband" from earlier.
        // It survives across every future request from this browser.
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        session.setAttribute("role", role);
        session.setAttribute("userId", userDAO.getUserIdByEmail(email));

        if (role.equals("LIBRARIAN")) {
            response.sendRedirect("librarian-dashboard.jsp");
        } else {
            response.sendRedirect("student-dashboard.jsp");
        }
    }
}