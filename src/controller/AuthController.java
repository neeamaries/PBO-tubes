package src.controller;

import src.dao.UserDAO;
import src.dao.ProfileDAO;
import src.model.User;
import src.model.Profile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth")
public class AuthController extends HttpServlet {
    private UserDAO userDAO;
    private ProfileDAO profileDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
        profileDAO = new ProfileDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            logout(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            register(request, response);
        } else if ("login".equals(action)) {
            login(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("regisFullname");
        String username = request.getParameter("regisUsername");
        String email = request.getParameter("regisEmail");
        String password = request.getParameter("regisPassword");
        String confirmPassword = request.getParameter("regisConfirmPassword");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Password dan Confirm Password tidak sama.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }
        System.out.println("=== DEBUG AuthController REGISTER ===");
System.out.println("Fullname : " + fullName);
System.out.println("Username : " + username);
System.out.println("Email    : " + email);
System.out.println("Password : " + password);
System.out.println("Confirm  : " + confirmPassword);

        User user = new User(0, username, email, password, null);

        boolean userSuccess = userDAO.register(user);

       if (!userSuccess || user.getUserID() == 0) {
    request.setAttribute("errorMessage", "Registrasi gagal saat menyimpan user ke database. Cek log Tomcat.");
    request.getRequestDispatcher("/register.jsp").forward(request, response);
    return;
}

        Profile profile = new Profile(
                0,
                user.getUserID(),
                fullName,
                "",
                ""
        );

        profileDAO.insertProfile(profile);

        request.setAttribute("successMessage", "Registrasi berhasil. Silakan login.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String emailOrUsername = request.getParameter("emailOrUsername");
        String password = request.getParameter("password");

        User user = userDAO.login(emailOrUsername, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userID", user.getUserID());

            response.sendRedirect("dashboard.jsp");
        } else {
            request.setAttribute("errorMessage", "Login gagal. Username/email atau password salah.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect("login.jsp");
    }
}