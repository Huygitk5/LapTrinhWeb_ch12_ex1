package murach.sql;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;


@WebServlet("/sqlGateway")
public class SqlGatewayServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String sqlStatement = request.getParameter("sqlStatement");
        String sqlResult = "";
        try {
            // load the driver
            Class.forName("org.postgresql.Driver");
            // get a connection
//            String dbURL = "jdbc:postgresql://dpg-d4jvcpali9vc73ddndkg-a.singapore-postgres.render.com:5432/ch12_ex1_sqlgateway_db"; 
//            String username = "ch12_ex1_sqlgateway_db_user";
//            String password = "nPN6IiLfaIuEEPXUxnyDpWRMCMV8DUMF";

            String dbURL = System.getenv("DB_URL");
            String username = System.getenv("DB_USER");
            String password = System.getenv("DB_PASS");

            if (dbURL == null) {
                dbURL = "jdbc:postgresql://dpg-d4jvcpali9vc73ddndkg-a.singapore-postgres.render.com:5432/ch12_ex1_sqlgateway_db";
            }
            if (username == null) {
                username = "ch12_ex1_sqlgateway_db_user";
            }
            if (password == null) {
                password = "nPN6IiLfaIuEEPXUxnyDpWRMCMV8DUMF";
            }
            Connection connection = DriverManager.getConnection(
                    dbURL, username, password);

            // create a statement
            Statement statement = connection.createStatement();

            // parse the SQL string
            sqlStatement = sqlStatement.trim();
            if (sqlStatement.length() >= 6) {
                String sqlType = sqlStatement.substring(0, 6);

                if (sqlType.equalsIgnoreCase("select")) {
                    // create the HTML for the result set
                    ResultSet resultSet
                            = statement.executeQuery(sqlStatement);
                    sqlResult = SQLUtil.getHtmlTable(resultSet);
                    resultSet.close();
                } else {
                    int i = statement.executeUpdate(sqlStatement);
                    if (i == 0) { // a DDL statement
                        sqlResult = 
                          "<p>The statement executed successfully.</p>";
                    } else { // an INSERT, UPDATE, or DELETE statement
                        sqlResult = 
                          "<p>The statement executed successfully.<br>"
                                + i + " row(s) affected.</p>";
                    }
                }
            }
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            sqlResult = "<p>Error loading the database driver: <br>"
                    + e.getMessage() + "</p>";
        } catch (SQLException e) {
            sqlResult = "<p>Error executing the SQL statement: <br>"
                    + e.getMessage() + "</p>";
        }
        HttpSession session = request.getSession();
        session.setAttribute("sqlResult", sqlResult);
        session.setAttribute("sqlStatement", sqlStatement);

        String url = "/index.jsp";
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}

