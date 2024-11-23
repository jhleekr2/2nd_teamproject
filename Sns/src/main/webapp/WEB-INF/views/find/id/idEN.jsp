<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>계정 찾기</title>
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
        .header {
            width: 100%;
            height: 12vh;
            background-color: #FFBC42;
            box-shadow: 0 4px 8px var(--shadow-color);
        }
        .find-account-container {
            background-color: var(--container-bg-color);
            width: 30%;
            padding: 40px;
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            border-radius: 10px;
            box-shadow: 0 4px 8px var(--shadow-color);
        }
        .find-account-container h2 {
            color: var(--button-bg-color);
            text-align: center;
            margin-bottom: 30px;
            font-size: 28px;
        }
        .find-account-links {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }
        .find-account-links a {
            color: var(--button-bg-color);
            text-decoration: none;
            font-size: 20px;
            text-align: center;
            padding: 15px;
            background-color: #2A2C30;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .find-account-links a:hover {
            background-color: #e59c3d;
        }
    </style>
</head>
<body>

<div class="header"></div>

<div class="find-account-container">
    <h2>계정 찾기</h2>
    <div class="find-account-links">
        <a href="./idEmail">이메일로 찾기</a>
        <a href="./idNumber">휴대폰 번호로 찾기</a>
    </div>
</div>

</body>
</html>