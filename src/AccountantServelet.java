import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/AccountantServelet")
public class AccountantServelet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sqlQuery = request.getParameter("sqlQuery");

        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/project4";
        String dbUsername = "theaccountant";
        String dbPassword = "theaccountant";

        try {
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            CallableStatement stmt = conn.prepareCall(sqlQuery);

            // Execute the stored procedure
            ResultSet rs = stmt.executeQuery();

            // Set response content type
            response.setContentType("text/html");

            // Redirect output to accountHome.jsp
            request.setAttribute("rs", rs);
            request.getRequestDispatcher("accountHome.jsp").forward(request, response);

            // Close resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Redirect to accountHome.jsp with error message
            request.setAttribute("errorMessage", "Error occurred: " + ex.getMessage());
            request.getRequestDispatcher("accountHome.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

