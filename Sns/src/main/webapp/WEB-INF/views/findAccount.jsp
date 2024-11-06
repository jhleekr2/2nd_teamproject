<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>계정 찾기</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #1F2124;
            font-family: Arial, sans-serif;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .find-account-container {
            width: 40%; /* 창의 너비 */
            padding: 60px;
            background-color: #383A3F; /* 큰 직사각형 배경 */
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .find-account-option {
            width: 90%;
            background-color: #2A2C30;
            color: #F6B352;
            font-size: 28px;
            text-align: center;
            padding: 30px;
            border-radius: 10px;
            cursor: pointer;
            margin-bottom: 15px; /* 간격을 작게 */
            transition: background-color 0.3s;
        }

        .find-account-option:last-child {
            margin-bottom: 0; /* 마지막 항목에 대한 여백 제거 */
        }

        .find-account-option:hover {
            background-color: #e59c3d;
        }

        .find-account-option:active {
            background-color: #d48834;
        }
    </style>
</head>
<body>

    <div class="find-account-container">
<a href="findid" class="find-account-option">아이디 찾기</a>
<a href="findpw" class="find-account-option">비밀번호 찾기</a>
    </div>

</body>
</html>
