<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시물 올리기</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #1F2124;
            color: white;
            font-family: Arial, sans-serif;
            height: 100vh;
            margin: 0;
            padding: 0;
        }
        
        .upload-container {
            width: 100%;
            max-width: 800px;
            background-color: #383A3F;
            padding: 40px;
            border-radius: 10px; /* 컨테이너 모서리 둥글게 */
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.5); /* 그림자 */
            display: flex;
            flex-direction: column;
            align-items: center;
            box-sizing: border-box;
        }

        .upload-container h2 {
            color: #F6B352;
            margin-bottom: 20px;
            text-align: center;
            font-size: 32px;
        }

        .upload-container textarea {
            width: 100%;
            height: 250px; /* 캡션 입력란 */
            padding: 20px;
            background-color: #2A2C30;
            color: white;
            border: none;
            border-radius: 5px; /* 캡션 모서리 약간 둥글게 */
            resize: none;
            font-size: 24px;
            margin-bottom: 20px;
            box-sizing: border-box;
        }

        .upload-container input[type="file"] {
            display: none;
        }

        .upload-label,
        .upload-container button {
            width: 100%;
            padding: 15px;
            text-align: center;
            font-size: 24px;
            border-radius: 5px; /* 버튼 및 레이블 모서리 둥글게 */
            cursor: pointer;
            color: white;
            box-sizing: border-box;
        }

        .upload-label {
            background-color: #2A2C30;
            margin-bottom: 20px;
            color: #F6B352;
        }

        .upload-container button {
            background-color: #F6B352;
            margin-top: 10px; /* 사진 선택과 게시 버튼 사이 간격 */
        }

        .upload-container button:hover {
            background-color: #e59c3d;
        }
    </style>
</head>
<body>

<div class="upload-container">
    <h2>게시물 올리기</h2>
    
    <!-- form을 사용해 POST 메서드로 데이터를 전송 -->
    <form action="/sns/main" method="POST">
        <!-- 캡션 입력 -->
        <textarea placeholder="캡션을 입력하세요..."></textarea>
        
        <!-- 사진 업로드 -->
        <label class="upload-label" for="file-upload">사진 선택</label>
        <input type="file" id="file-upload" accept="image/*">
        
        <!-- 게시 버튼 -->
        <button>게시</button>
    </form>
</div>

</body>
</html>
