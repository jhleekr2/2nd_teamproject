<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인 페이지</title>
    <style>
        :root {
            --background-color: #1F2124;
            --text-color: white;
            --container-bg-color: #383A3F;
            --input-bg-color: #2A2C30;
            --button-bg-color: #F6B352;
            --shadow-color: rgba(0, 0, 0, 0.2);
            --toggle-button-color: #4A4D52;
        }

        body {
            margin: 0;
            padding: 0;
            background-color: var(--background-color);
            font-family: Arial, sans-serif;
            color: var(--text-color);
        }
        .header {
            width: 100%;
            height: 12vh;
            background-color: #FFBC42;
            box-shadow: 0 4px 8px var(--shadow-color);
        }
        .login-container {
            background-color: var(--container-bg-color);
            width: 20%;
            padding: 60px;
            position: absolute;
            right: 120px;
            top: 50%;
            transform: translateY(-50%);
            border-radius: 10px;
            box-shadow: 0 4px 8px var(--shadow-color);
        }
        .login-container h2 {
            color: var(--button-bg-color);
            text-align: center;
            margin-bottom: 30px;
            font-size: 28px;
        }
        .login-container input[type="text"],
        .login-container input[type="password"],
        .login-container input[type="submit"] {
            width: 100%;
            padding: 15px;
            margin-bottom: 10px;
            border: none;
            border-radius: 5px;
            background-color: var(--input-bg-color);
            color: var(--text-color);
            font-size: 18px;
            box-sizing: border-box;
        }
        .login-container input[type="submit"] {
            background-color: var(--button-bg-color);
            font-size: 20px;
            cursor: pointer;
        }
        .login-container input[type="submit"]:hover {
            background-color: #e59c3d;
        }
        .login-links {
            text-align: center;
            margin-top: 20px;
        }
        .login-links a {
            color: var(--button-bg-color);
            text-decoration: none;
            font-size: 14px;
        }
        .login-links a:hover {
            text-decoration: underline;
        }
        .error-message {
            color: red;
            font-size: 16px;
            text-align: left;
            margin-bottom: 10px;
        }
        .password-container {
            position: relative;
        }
        .toggle-password {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            cursor: pointer;
            color: var(--toggle-button-color);
            font-size: 20px;
            padding: 5px;
        }
        .toggle-password i {
            font-size: 18px;
            color: var(--toggle-button-color);
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<div class="header"></div>

<div class="login-container">
    <h2>로그인</h2>
    <form id="loginForm" action="login" method="post">
        <c:if test="${not empty error}">
            <div class="error-message" id="errorMessage">${error}</div>
        </c:if>
        <input type="text" name="memberID" placeholder="아이디" required>
        <div class="password-container">
            <input type="password" name="password" id="password" placeholder="비밀번호" required>
            <button type="button" class="toggle-password" onclick="togglePasswordVisibility()">
                <i class="fas fa-eye"></i>
            </button>
        </div>
        <input type="submit" value="로그인">
    </form>
    <div class="login-links">
        <a href="./signup">회원가입</a> |
        <a href="/find/idpw">계정 찾기</a>
    </div>
</div>

<script>
    function togglePasswordVisibility() {
        const passwordInput = document.getElementById("password");
        const passwordToggle = document.querySelector(".toggle-password i");
        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            passwordToggle.classList.remove("fa-eye");
            passwordToggle.classList.add("fa-eye-slash");
        } else {
            passwordInput.type = "password";
            passwordToggle.classList.remove("fa-eye-slash");
            passwordToggle.classList.add("fa-eye");
        }
    }
</script>

</body>
</html>
