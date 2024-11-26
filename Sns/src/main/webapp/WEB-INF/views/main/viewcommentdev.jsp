<%@page import="java.util.Date"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- 현재 날짜를 Date 객체로 설정 --%>
<%-- 현재 시간 객체 생성 --%>
<c:set var="now" value="<%= new Date() %>" />

<%-- 현재 시간을 'yyyyMMdd' 형식으로 포맷 --%>
<fmt:formatDate value="${now}" pattern="yyyyMMdd" var="formattedNow" />
<h3>댓글 뷰 페이지</h3>
게시글 번호
<!--  URL 파라미터는 JSP EL과 JSTL로 가져올 수 있음. -->
<!--  URL 쿼리 파라미터를 EL을 사용하여 직접 가져오려면 param 객체를 사용. -->
<!--  param 객체는 HTTP 요청의 쿼리 문자열 파라미터를 접근할 수 있게 해주는 내장 객체 -->
<!--  다른 방법으로 URL 쿼리 파라미터를 가져오려면 URLSearchParams 객체를 사용하거나, -->
<!--  window.location.search를 파싱하는 방식이 있으며, AJAX의 경우에는 EL JSTL 방식 -->
<!--  사용이 불가하다는 사실에 주의! -->
${param.boardNo }
<hr>
댓글작성자 닉네임 조회<br>
${memberMap }
<hr>
댓글추천수 조회<br>
${recommendMap }
<hr>
로그인된 사용자의 댓글추천여부 조회<br>
${isCommentRecommendMap }
<div class = "comment">
<table>
<thead>
<tr>
	<th>댓글번호</th>
	<th>게시물번호</th>
	<th>작성회원번호</th>
	<th>작성회원닉</th>
	<th>작성내용</th>
	<th>작성날짜</th>
	<th>추천수</th>
	<th>추천여부</th>
	<th>추천/추천취소</th>
	<th>댓글삭제</th>
</tr>
</thead>
<tbody>
<c:forEach var="listcomment" items="${listcomment}">
<tr>
	<th>${listcomment.commentno}</th>
	<th>${listcomment.boardNo}</th>
	<th>${listcomment.memberno}</th>
	<th>${memberMap[listcomment.commentno]}</th>
	<th>${listcomment.content}</th>
	<th>
		<%-- 조회 날짜를 다른 Date객체로 설정 --%>
		<fmt:formatDate var="formattedcommdate" value="${listcomment.date }" pattern="yyyyMMdd"/>
		
		날짜 lt비교:${formattedcommdate lt formattedNow }<br>
		날짜 ge비교:${formattedcommdate ge formattedNow }<br>
		날짜 now:${formattedNow }<br>
		비교날짜:${formattedcommdate }<br>
		<c:choose>
			<c:when test="${formattedcommdate lt formattedNow }">
				<fmt:formatDate value="${listcomment.date }" pattern="yyyy-MM-dd"/>
			</c:when>
			<c:when test="${formattedcommdate ge formattedNow }">
				<fmt:formatDate value="${listcomment.date }" pattern="HH:mm"/>
			</c:when>
		</c:choose>
	</th>
	<th><div id="recommendcomm_${listcomment.commentno }"><h3>${recommendMap[listcomment.commentno] }</h3></div></th>
	<th>${isCommentRecommendMap[listcomment.commentno] }</th>
	<th>
	<div id="isrecommendcomm_${listcomment.commentno }">
		<h5><a href="javascript:void(0);" class="comlike-btn" data-commentno="${listcomment.commentno}">
		${isCommentRecommendMap[listcomment.commentno] == 0 ? '추천' : '추천취소'}
		</a></h5>
	</div>
	</th>
	<th>
		<c:if test="${listcomment.memberno eq param.memberno}">
		<a href="javascript:void(0);" class="comdel-btn" data-commentno="${listcomment.commentno}" data-boardno="${listcomment.boardNo}">댓글삭제</a>
		</c:if>
	</th>
</tr>
</c:forEach>
</tbody>
</table>
</div>
<!-- 2개 이상의 데이터를 a href 이후에 data- 형식으로 전달할 수 있다 -->

<!-- 앞으로 댓글 추가나 삭제하면 화면이 업데이트 되도록 AJAX를 이용한 구현이 필요 -->
<!-- 개발해야하는 기능 - 페이지네이션, 댓글 추가/삭제 및 추가/삭제시 댓글화면 업데이트 기능 -->
<hr>
<div class="addcomment-container">
<form action="./uploadcomment" method="POST">
    <!-- 게시물 번호와 로그인된 사용자 정보 전달 -->
    
    <input type="hidden" name="boardNo" value="${param.boardNo}">
<%--     <input type="hidden" name="memberno" value="${param.memberNo}"> --%>
<!-- 	<input type="hidden" name="memberno" value=2> -->
	<input type="hidden" name="memberno" value=${sessionScope.memberNo}>
<!-- 	<input type="hidden" name="memberno" value="2"> 이렇게 하면 memberno가 String 방식으로 전달 -->

	<!-- 사용자 정보는 임시로 2를 전달하는 것으로 개발했으나, 추후 정식으로 세션에서 값을 얻어오는 것으로 수정했음 -->
	<input type="text" id="upcomment_${param.boardNo }" name="upcomment_${param.boardNo }">
	<button class="addcomm_${param.boardNo}">댓글 입력</button>
</form>
</div>

<div class="paging">
    <ul class="pagination justify-content-center">
        <c:if test="${paging.curPage ne 1 }">
        <li class="page-item">
            <a class="page-link" href="javascript:void(0);" data-boardno="${param.boardNo}" data-curpage="1">&larr; 처음</a>
        </li>
        </c:if>
        <c:if test="${paging.startPage ne 1 }">
        <li class="page-item">
            <a class="page-link" href="javascript:void(0);" data-boardno="${param.boardNo}" data-curpage="${paging.startPage - paging.pageCount}">&laquo;</a>
        </li>
        </c:if>
        <c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}">
            <c:if test="${paging.curPage eq i }">
            <li class="page-item active">
                <a class="page-link" href="javascript:void(0);" data-boardno="${param.boardNo}" data-curpage="${i}">${i}</a>
            </li>
            </c:if>
            <c:if test="${paging.curPage ne i }">
            <li class="page-item">
                <a class="page-link" href="javascript:void(0);" data-boardno="${param.boardNo}" data-curpage="${i}">${i}</a>
            </li>
            </c:if>
        </c:forEach>
        <c:if test="${paging.endPage ne paging.totalPage }">
        <li class="page-item">
            <a class="page-link" href="javascript:void(0);" data-boardno="${param.boardNo}" data-curpage="${paging.startPage + paging.pageCount}">&raquo;</a>
        </li>
        </c:if>
        <c:if test="${paging.curPage ne paging.totalPage }">
        <li class="page-item">
            <a class="page-link" href="javascript:void(0);" data-boardno="${param.boardNo}" data-curpage="${paging.totalPage}">&rarr; 마지막</a>
        </li>
        </c:if>
    </ul>
</div>

<h3>댓글 마지막</h3>
