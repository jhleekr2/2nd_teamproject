<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/views/light/theme.jsp"%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">

<%-- 현재 날짜를 Date 객체로 설정 --%>
<%-- 현재 시간 객체 생성 --%>
<c:set var="now" value="<%= new Date() %>" />

<%-- 현재 시간을 'yyyyMMdd' 형식으로 포맷 --%>
<fmt:formatDate value="${now}" pattern="yyyyMMdd" var="formattedNow" />

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
		<td><a href="./update?boardNo=${content.boardNo}">${content.title}</a></td>
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

<div class="paging">
<div>

<ul class="pagination justify-content-center">

	<%-- 첫 페이지로 이동 --%>
	<c:if test="${paging.curPage ne 1 }">
	<li class="page-item">
		<a class="page-link" href="./mycontent">&larr; 처음</a>
	</li>
	</c:if>

	<%-- 이전 페이징 리스트로 이동 --%>
	<c:if test="${paging.startPage ne 1 }">
	<li class="page-item">
		<a class="page-link" href="./mycontent?curPage=${paging.startPage - paging.pageCount }&search=${paging.search}">&laquo;</a>
	</li>
	</c:if>

	<%-- 페이징 번호 리스트 --%>
	<c:forEach var="i" begin="${paging.startPage }" end="${paging.endPage }">
		<c:if test="${paging.curPage eq i }">
			<li class="page-item active">
				<a class="page-link" href="./mycontent?curPage=${i }&search=${paging.search}">${i }</a>
			</li>
		</c:if>
		<c:if test="${paging.curPage ne i }">
			<li class="page-item">
				<a class="page-link" href="./mycontent?curPage=${i }&search=${paging.search}">${i }</a>
			</li>
		</c:if>
	</c:forEach>
	
	<%-- 다음 페이징 리스트로 이동 --%>
	<c:if test="${paging.endPage ne paging.totalPage }">
	<li class="page-item">
		<a class="page-link" href="./mycontent?curPage=${paging.startPage + paging.pageCount }&search=${paging.search}">&raquo;</a>
	</li>
	</c:if>
	
	<%-- 마지막 페이지로 이동 --%>
	<c:if test="${paging.curPage ne paging.totalPage }">
	<li class="page-item">
		<a class="page-link" href="./mycontent?curPage=${paging.totalPage }&search=${paging.search}">&rarr; 마지막</a>
	</li>
	</c:if>
	
</ul>

</div>

</div>
</body>
</html>