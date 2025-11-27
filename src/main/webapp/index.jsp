<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head> 
        <meta charset="UTF-8">
        <title>Murach's Java Servlets and JSP</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head> 
    <body>
        <div class ="survey-container">
            <c:if test="${sqlStatement == null}">
                <c:set var="sqlStatement" value="select * from Users" />
            </c:if>

            <h1>The SQL Gateway</h1>
            <p>Enter an SQL statement and click the Execute button.</p>
            <p><b>SQL statement:</b></p>
            <form action="sqlGateway" method="post">
                <textarea class ="center" name="sqlStatement" cols="60" rows="8">${sqlStatement}</textarea><br>
               <button value="Execute">Execute</button>
            </form>

            <p><b>SQL result:</b></p>
            ${sqlResult}
        </div>  
    </body>
</html>
