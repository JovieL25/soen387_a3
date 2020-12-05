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
  </form>
</body>


</html>