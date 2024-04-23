import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/PartsInsertServlet")
public class PartsInsertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String pnum = request.getParameter("pnum");
        String pname = request.getParameter("pname");
        String color = request.getParameter("color");
        String weight = request.getParameter("weight");
        String city = request.getParameter("city");

        // JDBC Connection variables
        String url = "jdbc:mysql://localhost:3306/project4";
        String dbUsername = "dataentryuser";
        String dbPassword = "dataentryuser";

        try {
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Create SQL insert statement
            String insertQuery = "INSERT INTO parts (pnum, pname, color, weight, city) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, pnum);
                pstmt.setString(2, pname);
                pstmt.setString(3, color);
                pstmt.setString(4, weight);
                pstmt.setString(5, city);

                // Execute the insert statement
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Insertion successful, forward to the JSP page with success message
                    request.setAttribute("executionMessage", "New Parts record: (" + pnum + ", " + pname + ", " + color + ", " + weight + ", " + city + ") - successfully entered into the database.");
                } else {
                    // Insertion failed, forward to the JSP page with error message
                    request.setAttribute("executionMessage", "Failed to insert Parts record.");
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
