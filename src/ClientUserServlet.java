import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ClientUserServlet")
public class ClientUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sqlQuery = request.getParameter("sqlQuery");

        // JDBC Connection variables
        String url = "jdbc:mysql://localhost:3306/project4";
        String dbUsername = "client";
        String dbPassword = "client";

        StringBuilder tableHTML = new StringBuilder();

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
        RequestDispatcher dispatcher = request.getRequestDispatcher("clientHome.jsp");
        dispatcher.forward(request, response);
    }
}
