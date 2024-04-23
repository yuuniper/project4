import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/ShipmentInsertServlet")
public class ShipmentInsertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String snum = request.getParameter("shipment-snum");
        String pnum = request.getParameter("shipment-pnum");
        String jnum = request.getParameter("shipment-jnum");
        String quantityStr = request.getParameter("shipment-quantity");

        if (quantityStr == null || quantityStr.isEmpty()) {

            System.out.println(snum +  pnum +  jnum +quantityStr);
            // Handle empty quantity
            request.setAttribute("executionMessage", "Quantity cannot be empty.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("dataEntryHome.jsp");
            dispatcher.forward(request, response);
            return;
        }

        int quantity = Integer.parseInt(quantityStr);

        // JDBC Connection variables
        String url = "jdbc:mysql://localhost:3306/project4";
        String dbUsername = "dataentryuser";
        String dbPassword = "dataentryuser";

        try {
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Create SQL insert statement
            String insertQuery = "INSERT INTO shipments (snum, pnum, jnum, quantity) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, snum);
                pstmt.setString(2, pnum);
                pstmt.setString(3, jnum);
                pstmt.setInt(4, quantity);

                // Execute the insert statement
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Insertion successful, check business logic
                    String businessMessage = "Business logic not triggered.";
                    if (quantity >= 100) {
                        updateSupplierStatus(conn, snum);
                        businessMessage = "Business logic triggered.";
                    }
                    // Forward to the JSP page with success message
                    request.setAttribute("executionMessage", "New shipments record: (" + snum + ", " + pnum + ", " + jnum + ", " + quantity + ") - successfully entered into the database. " + businessMessage);
                } else {
                    // Insertion failed, forward to the JSP page with error message
                    request.setAttribute("executionMessage", "Failed to insert shipments record.");
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

    private void updateSupplierStatus(Connection conn, String snum) throws SQLException {
        // Increment the status of suppliers involved in shipments with quantity >= 100
        String updateQuery = "UPDATE suppliers SET status = status + 5 WHERE snum IN " +
                "(SELECT snum FROM shipments WHERE quantity >= 100)";
        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.executeUpdate();
        }
    }
}
