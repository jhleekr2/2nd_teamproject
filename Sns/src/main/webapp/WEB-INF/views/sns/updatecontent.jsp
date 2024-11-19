<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html> 
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시물 수정하기</title>
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

		.upload-container input[type="text"] {
			width: 100%;
			height: 25px;
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
    <h2>게시물 수정하기</h2>
    <!-- form을 사용해 POST 메서드로 데이터를 전송 -->
    <!-- 파일 업로드를 해야 하기에 multipart 사용 -->
<!--     <form action="/sns/main" method="POST" enctype="multipart/form-data"> -->
	<!-- 기존 main페이지 POST방식에서 upload POST 방식으로 수정함 -->
    <form action="./update" method="POST" enctype="multipart/form-data">
    	<!-- 제목 입력 -->
    	<table>
    	<tr>
    	<td>제목</td>
    	<td>
    		<input type="text" id="title" name="title" value="${content.title }">
    	</td>
    	</tr>
    	
        <!-- 캡션 입력 -->
        <tr>
        <td>내용</td>
        <td>
        	<textarea id="content" name="content">${content.content }</textarea>
    	</td>
    	</table>
        <div id="beforefile">
        <table>
        	<c:forEach var="filelist" items="${filelist}" >
        	<tr>
        		<td>
				<a href="/sns/image?fileno=${filelist.fileno }">${filelist.original}</a>
				</td>
				<td>
					<span id="delFile">
					<i class="bi bi-trash-fill"></i>
					</span>
					<input type="checkbox" name="delCheck" value="${filelist.fileno }"> 삭제
				</td>
        	</tr>
        	</c:forEach>
        </table>
        
        </div>
        <!-- 사진 업로드 -->
        <label class="upload-label" for="file-upload">사진 선택</label>
        <input type="file" id="file-upload" name="file" accept="image/*">
        <input type="hidden" id="boardNo" name="boardNo" value="${content.boardNo }">
        <input type="hidden" id="memberno" name="memberno" value="${content.memberno }">
        <input type="hidden" id="isopened" name="isopened" value="${content.isopened }">
  		
        <!-- 게시 버튼 -->
        <button>게시</button>
    </form>
</div>

</body>
</html>
