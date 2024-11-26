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
<style type="text/css">
/* 페이지네이션 CSS */
.paging {
	display: flex;
	justify-content: center; /* 페이지네이션을 가로 중앙 정렬 */
	padding: 20px 0;
	color: white;
}

.paging ul {
	list-style: none; /* 기본 ul의 점 스타일 제거 */
	margin: 0;
	padding: 0;
	display: flex;
}

.paging li {
	margin: 0 5px;
}

.paging a {
	text-decoration: none;
	padding: 10px 15px;
	color: white;
	border: 1px solid #ddd; /* 기본 테두리 */
	border-radius: 5px;
	font-size: 14px;
	transition: background-color 0.3s ease, color 0.3s ease;
}

/* 활성 페이지 스타일 */
.paging a:hover,
.paging .active a {
	background-color: #F6B352;
	color: white;
	border-color: #F6B352;
}

/* 비활성화된 페이지 스타일 (이전/다음 버튼 비활성화) */
.paging .disabled a {
	color: #ccc;
	pointer-events: none;
	border-color: #ccc;
}

/* 페이지네이션에 첫 번째 및 마지막 페이지 스타일 */
.paging .first,
.paging .last {
	font-weight: bold;
}


</style>
<body>
<h3>작성한 게시글</h3>
<hr>

<div class="content-wrapper" id="contentWrapper">
<table>
<thead>
	<tr>
	<td>게시물번호</td>
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
		<td><a href="./update?boardNo=${content.boardNo}">${content.title}</a></td>
		<td>
			<%-- 조회 날짜를 다른 Date객체로 설정 --%>
			<fmt:formatDate var="formattedcondate" value="${content.date }" pattern="yyyyMMdd"/>
			<c:choose>
				<c:when test="${formattedcondate lt formattedNow }">
					<fmt:formatDate value="${content.date }" pattern="yyyy-MM-dd"/>
				</c:when>
				<c:when test="${formattedcondate ge formattedNow }">
					<fmt:formatDate value="${content.date }" pattern="HH:mm"/>
				</c:when>
			</c:choose>
		</td>
		<td>${content.isopened}</td>
		<td>${content.ad}</td>
		<td>
			<%-- 조회 날짜를 다른 Date객체로 설정 --%>
			<fmt:formatDate var="formattedupdate" value="${content.update }" pattern="yyyyMMdd"/>
			<c:choose>
				<c:when test="${formattedupdate lt formattedNow }">
					<fmt:formatDate value="${content.update }" pattern="yyyy-MM-dd"/>
				</c:when>
				<c:when test="${formattedupdate ge formattedNow }">
					<fmt:formatDate value="${content.update }" pattern="HH:mm"/>
				</c:when>
			</c:choose>
		</td>
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