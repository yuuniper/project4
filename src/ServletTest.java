// A simple servlet to process an HTTP get request.
// Main servlet in first-example web-app

// Users of Tomcat 10 onwards should be aware that, as a result of the move from Java EE to Jakarta EE as part of the
// transfer of Java EE to the Eclipse Foundation, the primary package for all implemented APIs has changed
// from javax.* to jakarta.*. This will almost certainly require code changes to enable applications to migrate
// from Tomcat 9 and earlier to Tomcat 10 and later.

//import javax.servlet.*;   //used for Tomcat 9.x and earlier only
//import javax.servlet.http.*;  //used for Tomcat 9.x and earlier only
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@SuppressWarnings("serial")
@WebServlet("/test")
public class ServletTest extends HttpServlet {

    // process "get" requests from clients
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException  {

        response.setContentType( "text/html" );
        PrintWriter out = response.getWriter();

        // send HTML5 page to client
        // start HTML5 document
        out.println("<html>");
        out.println( "<meta charset=\"utf-8\">" );
        // head section of document
        out.println( "<head>" );
        out.println( "<style type='text/css'>");
        out.println( "<!--  body{background-color:black; color:white; font-family: Verdana, Arial, sans-serif; text-align: center;}");
        out.println( " h1{font-size:100pt; text-align:center;} h2{font-family:inherit; font-size:60pt;}");
        out.println( " #one{color:magenta;} #two{color:yellow;} #three{color:red;} #four{color:lime;} #five{color:cyan;}");
        out.println( "-->");
        out.println( "</style>");
        out.println( "<title>Welcome to Servlets!</title>" );
        out.println( "</head>" );
        // body section of document
        out.println( "<body>" );
        out.println( "<h1><span id=\"one\">H</span><span id=\"two\">e</span><span id=\"three\">l</span>"
                + "<span id=\"four\">l</span><span id=\"five\">o</span>!!</h1>");
        out.println( "<h2>Welcome To The Exciting World Of Servlet Technology!</h2>" );
        out.println( "</body>" );
        // end HTML5 document
        out.println( "</html>" );
        out.close();  // close stream to complete the page

    } //end doGet() method

} //end WelcomeServlet class
