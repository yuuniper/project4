import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/SuppliersInsertServlet")
public class SuppliersInsertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String snum = request.getParameter("snum");
        String sname = request.getParameter("sname");
        String status = request.getParameter("status");
        String city = request.getParameter("city");

        // JDBC Connection variables
        String url = "jdbc:mysql://localhost:3306/project4";
        String dbUsername = "dataentryuser";
        String dbPassword = "dataentryuser";

        try {
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Create SQL insert statement
            String insertQuery = "INSERT INTO suppliers (snum, sname, status, city) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, snum);
                pstmt.setString(2, sname);
                pstmt.setString(3, status);
                pstmt.setString(4, city);

                // Execute the insert statement
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Insertion successful, forward to the JSP page with success message
                    request.setAttribute("executionMessage", "New Suppliers record: (" + snum + ", " + sname + ", " + status + ", " + city + ") - successfully entered into the database.");
                } else {
                    // Insertion failed, forward to the JSP page with error message
                    request.setAttribute("executionMessage", "Failed to insert Suppliers record.");
                }
            }

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database errors
            request.setAttribute("executionMessage", "Database error occurred: " + ex.getMessage());
        }

        // Forward the request to the JSP page to display the results
        RequestDispatcher dispatcher = request.getRequestDispatcher("dataEntryHome.jsp");
        dispatcher.forward(request, response);
    }
}
