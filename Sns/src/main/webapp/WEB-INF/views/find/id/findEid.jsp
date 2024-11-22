<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>계정 찾기 결과</title>
    <style>
        :root {
            --background-color: #1F2124;
            --text-color: white;
            --container-bg-color: #383A3F;
            --button-bg-color: #F6B352;
            --shadow-color: rgba(0, 0, 0, 0.2);
        }

        body {
            margin: 0;
            padding: 0;
            background-color: var(--background-color);
            font-family: Arial, sans-serif;
            color: var(--text-color);
        }
        .result-container {
            background-color: var(--container-bg-color);
            width: 40%;
            padding: 50px;
            margin: 0 auto;
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            border-radius: 10px;
            box-shadow: 0 6px 12px var(--shadow-color);
            text-align: center;
        }
        .result-container h2 {
            color: var(--button-bg-color);
            margin-bottom: 30px;
            font-size: 28px;
        }
        .message {
            font-size: 22px;
            line-height: 1.6;
            margin-bottom: 30px;
        }
        .login-btn {
            display: inline-block;
            background-color: var(--button-bg-color);
            color: white;
            border: none;
            padding: 15px 30px;
            font-size: 18px;
            cursor: pointer;
            border-radius: 5px;
            text-decoration: none;
            transition: background-color 0.3s ease;
            margin-top: 20px;
        }
        .login-btn:hover {
            background-color: #e59c3d;
        }
    </style>
</head>
<body>

<div class="result-container">
    <h2>계정 찾기 결과</h2>
    <c:choose>
        <c:when test="${not empty memberId}">
            <p class="message"><strong>${memberName}</strong>님의 ID는 <strong>${memberId}</strong>입니다.</p>
        </c:when>
        <c:otherwise>
            <p class="message">${error}</p>
        </c:otherwise>
    </c:choose>
    <a href="${pageContext.request.contextPath}/member/login" class="login-btn">로그인하기</a>
</div>

</body>
</html>
