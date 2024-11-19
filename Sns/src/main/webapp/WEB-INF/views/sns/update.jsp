<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/views/light/theme.jsp"%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>작성한 게시글</h3>
<hr>

<div class="content-wrapper" id="contentWrapper">
<table>
<thead>
	<tr>
	<td>게시물번호</td>
	<td>회원번호</td>
	<td>제목</td>
	<td>작성날짜</td>
	<td>공개여부</td>
	<td>광고여부</td>
	<td>업데이트</td>
	<td>게시물 삭제</td>
	</tr>
</thead>
<tbody>
	<c:forEach var="content" items="${contentlist }">
	<tr>
		<td>${content.boardNo}</td>
		<td>${content.memberno}</td>
		<td><a href="./updatecontent?boardNo=${content.boardNo}">${content.title}</a></td>
		<td>${content.isopened}</td>
		<td>${content.date}</td>
		<td>${content.ad}</td>
		<td>${content.update}</td>
		<td><a href="./delete?boardNo=${content.boardNo }">삭제</a></td>
	</tr>
	</c:forEach>
</tbody>
</table>

</div>
</body>
</html>