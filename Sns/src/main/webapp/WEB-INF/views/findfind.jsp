<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>아이디 찾기 결과</title>
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

        .result-container {
            width: 40%;
            padding: 40px;
            background-color: #383A3F;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .result-container h2 {
            color: #F6B352;
            text-align: center;
            margin-bottom: 30px;
        }

        .result-container p {
            color: white;
            font-size: 18px;
            margin-bottom: 10px;
            padding: 15px;
            background-color: #2A2C30;
            border-radius: 5px;
            text-align: left; /* 왼쪽 정렬 */
        }

        .result-container .back-btn {
            display: block;
            width: 100%;
            padding: 15px;
            text-align: center;
            background-color: #F6B352;
            color: white;
            font-size: 18px;
            border-radius: 5px;
            text-decoration: none;
            margin-top: 20px;
            box-sizing: border-box;
        }

        .result-container .back-btn:hover {
            background-color: #e59c3d;
        }

    </style>
</head>
<body>

    <div class="result-container">
        <h2>아이디 찾기 결과</h2>
        <p>이름: </p>
        <p>번호: </p>
        <a href="main" class="back-btn">로그인 화면으로 돌아가기</a>
    </div>

</body>
</html>
