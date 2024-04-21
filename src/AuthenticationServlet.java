import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@WebServlet("/authenticationServlet")
public class AuthenticationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // JDBC Connection variables
        String url = "jdbc:mysql://localhost:3306/credentialsDB";
        String dbUsername = "root";
        String dbPassword = "Mochi123";

        try {
          DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            String query = "SELECT * FROM usercredentials WHERE login_username=? AND login_password=?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Authentication successful, redirect to success page
                        //response.sendRedirect("success.jsp");
                        System.out.println("We did it");
                    } else {
                        // Authentication failed, redirect to error page
                        //response.sendRedirect("errorpage.html");
                        System.out.println("No");

                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database connection errors
        }


    }
}