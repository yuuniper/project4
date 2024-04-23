import java.io.IOException;
import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RootUserServlet")
public class RootUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sqlQuery = request.getParameter("sqlQuery");

        // JDBC Connection variables
        String url = "jdbc:mysql://localhost:3306/project4";
        String dbUsername = "root";
        String dbPassword = "Mochi123";

        StringBuilder tableHTML = new StringBuilder();
        boolean businessLogicTriggered = false;

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            try (PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {
                if (sqlQuery.trim().toUpperCase().startsWith("SELECT")) {
                    // If it's a SELECT statement, execute it and print the result
                    ResultSet rs = pstmt.executeQuery();
                    tableHTML.append("<table border='1'>");
                    // Print column names
                    tableHTML.append("<tr>");
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        tableHTML.append("<th>").append(rs.getMetaData().getColumnName(i)).append("</th>");
                    }
                    tableHTML.append("</tr>");
                    // Print rows
                    while (rs.next()) {
                        tableHTML.append("<tr>");
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            tableHTML.append("<td>").append(rs.getString(i)).append("</td>");
                        }
                        tableHTML.append("</tr>");
                    }
                    tableHTML.append("</table>");
                } else {
                    // If it's not a SELECT statement, execute it
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        tableHTML.append("The statement executed successfully.<br>");
                        tableHTML.append("A total of ").append(rowsAffected).append(" row(s) were updated.<br>");
                        if (sqlQuery.trim().toUpperCase().startsWith("INSERT")) {
                            // Extract quantity value from the SQL query
                            String[] values = sqlQuery.split(",");
                            String quantityStr = values[3].trim(); // Extract the quantity string
                            // Remove non-numeric characters
                            String quantityOnlyDigits = quantityStr.replaceAll("[^0-9]", "");
                            if (!quantityOnlyDigits.isEmpty()) { // Check if quantity is not empty
                                int quantity = Integer.parseInt(quantityOnlyDigits);
                                if (quantity > 100) {
                                    businessLogicTriggered = true;
                                    // Perform business logic for status update
                                    int updatedStatusCount = updateSupplierStatus(conn, pstmt); // Pass PreparedStatement to get affected supplier
                                    tableHTML.append("Business Logic Detected! - Updating Supplier Status.<br>");
                                    tableHTML.append("Business Logic updated ").append(updatedStatusCount).append(" supplier status marks.<br>"); // Display count of updated status marks
                                } else {
                                    tableHTML.append("Business Logic Not Triggered!<br>");
                                }
                            } else {
                                tableHTML.append("No quantity value found in the SQL query.<br>");
                            }
                        } else {
                            tableHTML.append("Business Logic Not Triggered!<br>");
                            tableHTML.append("SQL Query: ").append(sqlQuery).append("<br>"); // Debugging: Print SQL query
                        }

                    } else {
                        tableHTML.append("The statement executed successfully.<br>");
                        tableHTML.append("No rows affected.<br>");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database connection errors
            tableHTML.append("<span style='color: red;'>Error executing the SQL statement: ").append(ex.getMessage()).append("</span>");
        }

        request.setAttribute("tableHTML", tableHTML.toString());
        RequestDispatcher dispatcher = request.getRequestDispatcher("rootHome.jsp");
        dispatcher.forward(request, response);
    }

    private int updateSupplierStatus(Connection conn, PreparedStatement pstmt) throws SQLException {
        String sqlQuery = pstmt.toString();
        String[] values = sqlQuery.split("\\(")[1].split(",");
        String supplierNum = values[0].replaceAll("'", "").trim();
        System.out.println("Supplier number extracted: " + supplierNum); // Debugging
        if (!supplierNum.isEmpty()) {
            String updateStatusQuery = "UPDATE suppliers SET status = status + 5 WHERE snum = "  + supplierNum;

            System.out.println(updateStatusQuery);
            PreparedStatement updateStmt = conn.prepareStatement(updateStatusQuery);
            int updatedRows = updateStmt.executeUpdate(); // Execute the update statement

            return updatedRows;

        } else {
            return 0; // No supplier number found, no update performed
        }
    }
}
