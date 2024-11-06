<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html lang="ko"> 
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>아이디 찾기</title>
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

        .find-id-container {
            width: 40%;
            padding: 40px;
            background-color: #383A3F;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .find-id-container h2 {
            color: #F6B352;
            text-align: center;
            margin-bottom: 30px;
        }

        .find-id-container input[type="text"], 
        .find-id-container input[type="submit"] {
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

        .find-id-container input[type="submit"] {
            background-color: #F6B352;
            cursor: pointer;
            font-size: 20px;
        }

        .find-id-container input[type="submit"]:hover {
            background-color: #e59c3d;
        }
    </style>
</head>
<body>

    <div class="find-id-container">
        <h2>아이디 찾기</h2>
    <form action="findid" method="POST">
    <input type="text" name="name" placeholder="이름" required>
    <input type="text" name="phone" placeholder="번호" required>
    <input type="submit" value="찾기">
</form>

    </div>

</body>
</html>
