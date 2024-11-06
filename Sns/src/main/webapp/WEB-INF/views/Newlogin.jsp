<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html lang="ko"> 
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 페이지</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #1F2124; /* 전체 배경 색 */
            font-family: Arial, sans-serif;
        }

        .header, .footer {
            width: 100%;
            height: 8.33vh; /* 화면의 12분의 1 */
            background-color: #1F2124; /* 상단과 하단 줄 배경색을 전체 배경과 동일하게 설정 */
        }

        .signup-container {
            background-color: #383A3F; /* 회원가입 창 배경 */
            width: 30%; /* 화면의 3분의 1 */
            padding: 60px;
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%); /* 중앙으로 이동 */
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .signup-container h2 {
            color: #F6B352;
            text-align: center;
            margin-bottom: 30px;
            font-size: 28px;
        }

        .signup-container input[type="text"], 
        .signup-container input[type="password"], 
        .signup-container input[type="email"], 
        .signup-container input[type="submit"] {
            width: 100%;
            padding: 15px;
            margin-bottom: 20px;
            border: none;
            border-radius: 5px;
            background-color: #2A2C30;
            color: white;
            font-size: 18px;
            box-sizing: border-box;
        }

        .signup-container input[type="submit"] {
            background-color: #F6B352;
            font-size: 20px;
            cursor: pointer;
        }

        .signup-container input[type="submit"]:hover {
            background-color: #e59c3d;
        }
    </style>
</head>
<body>

    <!-- 상단 줄 (배경색을 전체 배경과 일치시킴) -->
    <div class="header"></div>

    <!-- 회원가입 창 -->
    <div class="signup-container">
        <h2>회원가입</h2>
        <form action="/sns/main" method="GET">
            <input type="text" name="name" placeholder="이름" required>
            <input type="text" name="phone" placeholder="번호" required>
            <input type="text" name="address" placeholder="주소" required>
            <input type="email" name="email" placeholder="아이디 (이메일)" required>
            <input type="password" name="password" placeholder="비밀번호" required>
            <input type="text" name="nickname" placeholder="닉네임" required>
            <input type="submit" value="회원가입">
        </form>
    </div>

    <!-- 하단 줄 (배경색을 전체 배경과 일치시킴) -->
    <div class="footer"></div>

</body>
</html>
