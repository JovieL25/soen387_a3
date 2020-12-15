<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>


<html lang="en">

<head>
  <meta charset="UTF-8">

  <title>Login</title>

  <link rel="stylesheet" href="bootstrap.css">
  <link rel="stylesheet" href="login.css">
</head>

<body class="text-center">
  <form action="DownloadServlet" method="POST" class="form-login">

    <c:if test="${not empty signupError}">
      <script>
        window.addEventListener("load",function() {
          alert("Sign up feature not available");
        });
      </script>
    </c:if>
    <c:if test="${not empty loginError}">
      <script>
        window.addEventListener("load", function () {
          alert("Please enter correct username and password");
        });
      </script>
    </c:if>


    <h1>Message Board</h1>
    <h1 class="h3 font-weight-normal">Please login</h1>
    <br>

    <label for="login-email" class="sr-only">Email</label>
    <input type="text" id="login-email" name="login-email" placeholder="Email" required autofocus class="form-control">
    <br>

    <label for="login-password" class="sr-only">Password</label>
    <input type="password" id="login-password" name="login-password" placeholder="Password" required class="form-control">
    <br>

    <input type="submit" name="login" value="Sign in" class="btn btn-primary">
    <input type="submit" name="register" value="Sign up" class="btn btn-primary">

    <br>
    <div class="error-msg">
      <%
        String signuperr_msg=(String)request.getAttribute("signupError");
        if(signuperr_msg!=null)
          out.println("<div><font color=red size=2px><strong>SIGNUP ERROR: </strong>"+signuperr_msg+"</font></div>");
      %>
      <%
        String loginerr_msg=(String)request.getAttribute("loginError");
        if(loginerr_msg!=null)
          out.println("<div><font color=red size=2px><strong>LOGIN ERROR: </strong>"+loginerr_msg+"</font></div>");
      %>
      <%
        String grouperr_msg=(String)request.getAttribute("groupError");
        if(grouperr_msg!=null)
          out.println("<div><font color=red size=2px><strong>GROUP ERROR: </strong>"+grouperr_msg+"</font></div>");
      %>
      <%
        String memerr_msg=(String)request.getAttribute("membershipError");
        if(memerr_msg!=null)
          out.println("<div><font color=red size=2px><strong>MEMBERSHIP ERROR: </strong>"+memerr_msg+"</font></div>");
      %>
    </div>

  </form>
</body>


</html>