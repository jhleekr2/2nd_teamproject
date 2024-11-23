<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>중복 계정 발견</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #1F2124;
            font-family: Arial, sans-serif;
            color: white;
        }
        .container {
            background-color: #383A3F;
            width: 35%;
            padding: 50px;
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            text-align: center;
        }
        h1 {
            color: #FF6B6B;
            margin-bottom: 20px;
            font-size: 28px;
        }
        p {
            font-size: 18px;
            line-height: 1.6;
        }
        .error-message {
            color: #FF6B6B;
            font-size: 20px;
            margin: 30px 0;
        }
        .btn {
            background-color: #F6B352;
            color: white;
            border: none;
            padding: 15px 30px;
            font-size: 18px;
            cursor: pointer;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s ease;
        }
        .btn:hover {
            background-color: #e59c3d;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>중복 계정 발견</h1>
    <p class="error-message"><c:out value="${error != null ? error : '해당 전화번호로 여러 계정이 등록되어 있어 계정 확인이 어렵습니다.'}"/></p>
    <a href="${pageContext.request.contextPath}/member/login" class="btn">로그인 화면으로 돌아가기</a>
</div>

</body>
</html>
