<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/light/theme.jsp" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인 페이지</title>
    <style>
        /* CSS 변수 */
        :root {
            --background-color: #1F2124;
            --text-color: white;
            --container-bg-color: #383A3F;
            --input-bg-color: #2A2C30;
            --button-bg-color: #F6B352;
            --shadow-color: rgba(0, 0, 0, 0.2);
        }

        .light-mode {
            --background-color: #F5F5DC;
            --text-color: black;
            --container-bg-color: white;
            --input-bg-color: #F0F0F0;
            --button-bg-color: #FFBC42;
            --shadow-color: rgba(0, 0, 0, 0.15);
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
            right: 50px;
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
            margin-bottom: 20px;
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
            margin-top: 40px;
        }
        .login-links a {
            color: var(--button-bg-color);
            text-decoration: none;
            font-size: 14px;
        }
        .login-links a:hover {
            text-decoration: underline;
        }
        .mode-toggle {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background-color: var(--button-bg-color);
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            box-shadow: 0 4px 8px var(--shadow-color);
        }
    </style>
</head>
<%-- 세션에 저장된 테마를 기반으로 body 클래스 설정 --%>
<body class="<%= theme.equals("dark") ? "" : "light-mode" %>">

    <div class="header"></div>

    <div class="login-container">
        <h2>로그인</h2>
        <form action="main" method="post">
            <input type="text" name="username" placeholder="아이디" required>
            <input type="password" name="password" placeholder="비밀번호" required>
            <input type="submit" value="로그인">
        </form>
        <div class="login-links">
            <a href="newlogin">회원가입</a> |
            <a href="findAccount">계정 찾기</a>
        </div>
    </div>

    <button class="mode-toggle" onclick="toggleMode()">화이트/블랙 모드 전환</button>

    <script>
        function toggleMode() {
            const currentTheme = document.body.classList.contains('light-mode') ? 'dark' : 'light';
            document.body.classList.toggle('light-mode');
            
            // 세션에 테마 저장 요청
            fetch('setTheme.jsp?theme=' + currentTheme, { method: 'GET' });
        }
    </script>

</body>
</html>
