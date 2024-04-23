import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/JobInsertServlet")
public class JobInsertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String jnum = request.getParameter("jnum");
        String jname = request.getParameter("jname");
        String numworkers = request.getParameter("numworkers");
        String city = request.getParameter("city");

        // JDBC Connection variables
        String url = "jdbc:mysql://localhost:3306/project4";
        String dbUsername = "dataentryuser";
        String dbPassword = "dataentryuser";

        try {
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Create SQL insert statement
            String insertQuery = "INSERT INTO jobs (jnum, jname, numworkers, city) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, jnum);
                pstmt.setString(2, jname);
                pstmt.setString(3, numworkers);
                pstmt.setString(4, city);

                // Execute the insert statement
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Insertion successful, forward to the JSP page with success message
                    request.setAttribute("executionMessage", "New Job record: (" + jnum + ", " + jname + ", " + numworkers + ", " + city + ") - successfully entered into the database.");
                } else {
                    // Insertion failed, forward to the JSP page with error message
                    request.setAttribute("executionMessage", "Failed to insert Job record.");
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

