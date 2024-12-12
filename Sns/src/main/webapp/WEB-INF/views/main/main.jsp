<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">

<!DOCTYPE html>

<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SNS 페이지</title>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	background-color: #1F1F1F;
	font-family: Arial, sans-serif;
	color: white;
	overflow: hidden;
	user-select: none;
}

/* 스크롤바 완전히 숨기기 */
.content-wrapper::-webkit-scrollbar {
	display: none;
}

.content-wrapper {
	-ms-overflow-style: none;
	scrollbar-width: none;
}

.top-bar {
	background-color: #FFBC42;
	padding: 8px;
	display: flex;
	justify-content: flex-end;
	align-items: center;
	position: fixed;
	top: 0;
	width: 100%;
	z-index: 10;
	height: 70px;
	gap: 15px;
}

.user-info-container {
	display: flex;
	align-items: center;
	gap: 15px;
	margin-right: 50px;
	position: relative;
	cursor: pointer;
}

.user-info img {
	width: 40px;
	height: 40px;
	border-radius: 50%;
}

.username {
	font-size: 18px;
	color: white;
}

.notification-icon {
	font-size: 24px;
	color: white;
	cursor: pointer;
}

.dropdown-menu {
	display: none;
	position: absolute;
	top: 60px;
	right: 0;
	background-color: #383A3F;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	border-radius: 5px;
	overflow: hidden;
	z-index: 10;
}

.dropdown-menu a {
	display: block;
	padding: 10px 20px;
	color: #FFFFFF;
	text-decoration: none;
	font-size: 16px;
}

.dropdown-menu a:hover {
	background-color: #2A2C30;
}

.search-bar {
	position: absolute;
	left: 50%;
	transform: translateX(-50%);
}

.search-bar input[type="text"] {
	width: 500px;
	padding: 10px;
	border: none;
	border-radius: 5px;
	background-color: #2A2C30;
	color: white;
	font-size: 16px; 
}

.search-bar input[type="text"]::placeholder {
	color: #F6B352;
}

.content-wrapper {
	display: flex;
	flex-direction: row;
	overflow-x: auto;
	overflow-y: hidden;
	scroll-snap-type: x mandatory;
	gap: 60px;
	padding: 0px 40px 100px calc(50vw - 300px);
	height: 100vh;
	align-items: center;
	scroll-behavior: smooth;
	cursor: grab;
	background-color: #171717;
}

.post {
	flex: 0 0 65vw;
	height: 80vh;
	background-color: #383A3F;
	border-radius: 10px;
	padding: 20px;
	box-sizing: border-box;
	display: flex;
	scroll-snap-align: center;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
	margin-top: 50px;
	transition: opacity 0.3s;
}

.left-section, .right-section {
	flex: 1;
	padding: 20px;
}

.left-section {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 15px;
    border-right: 1px solid #2A2C30;
    padding-top: 50px; /* 프로필 오버레이와 이미지를 분리할 수 있도록 여백 추가 */
}

.profile-overlay {
    position: absolute;
    top: 0px; /* 프로필 오버레이 위치를 상단에 고정 */
    left: 20px;
    display: flex;
    align-items: center;
    gap: 10px;
    background-color: rgba(0, 0, 0, 0.6);
    padding: 5px 10px;
    border-radius: 10px;
}

.profile-overlay img {
	width: 30px;
	height: 30px;
	border-radius: 50%;
}

.profile-overlay .username {
	font-size: 14px;
	color: white;
}

.left-section img {
    width: 100%;
    height: auto;
    border-radius: 5px;
    margin-top: 10px; /* 이미지와 오버레이 간 간격을 추가 */
}
.post-actions {
	display: flex;
	justify-content: center;
	gap: 10px;
	width: 100%;
	margin-top: 10px;
}

.post-actions span {
	color: #F6B352;
	cursor: pointer;
}

.right-section {
	overflow-y: auto;
	height: 100%;
}

.comment {
	background-color: #2A2C30;
	padding: 10px;
	margin-bottom: 10px;
	border-radius: 5px;
	color: white;
}

.bottom-menu {
	position: fixed;
	bottom: 0;
	width: 100%;
	background-color: #E0E3DA;
	padding: 10px;
	display: flex;
	justify-content: center;
	gap: 30px;
	box-shadow: 0 -4px 8px rgba(0, 0, 0, 0.2);
	z-index: 5;
}

.bottom-menu a {
	color: #1F2124;
	text-decoration: none;
	font-size: 18px;
}

.bottom-menu a:hover {
	text-decoration: underline;
}

.addcomment-container button {

	background-color: #F6B352;
	text-align: center;
	width: 20%;
	height: 30px;
	border-radius: 5px; /* 버튼 및 레이블 모서리 둥글게 */
	margin-top: 2px;
	float: right;
}

.addcomment-container input {
	width: 75%;
	height: 15px;
	padding: 10px;
	background-color: #383A3F;
	color: white;
	border: none;
	border-radius: 5px; /* 캡션 모서리 약간 둥글게 */
}

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

img.profile {
    width: 60px;
    height: 60px;
    aspect-ratio: 1 / 1; /* Ensures the image maintains a square aspect ratio */
    border-radius: 5px; /* Optional: Add rounded corners if desired */
}

.boardno {
	color: #FFFFFF;
	font-size: 30px;
}

.search-bar {
	color: white;
	font-size: 30px;
}

.post-content {
	font-size: 24px;
}
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

</head>
<body>
	<div class="top-bar">
		<div class="user-info-container" onclick="toggleDropdown()">
			<span class="notification-icon">&#x1F514;</span> <img
				src="/resources/profile.png" alt="프로필" class=profile>
<%-- 					세션 아이디 : <%=session.getId() %><br> --%>
<%-- 					세션 로그인 회원번호 : ${sessionScope.memberNo}<br> --%>
<%-- 					세션 로그인 회원번호 : <%=session.getAttribute("memberNo") %> --%>
			<div class="username">
			<c:if test="${sessionScope.islogin }"><%=session.getAttribute("memberID") %>님 안녕하세요</c:if>
			<c:if test="${empty sessionScope.islogin }">로그인하세요</c:if>
			</div>

			<!-- 드롭다운 메뉴 -->
			<div class="dropdown-menu" id="dropdownMenu">
				<c:if test="${sessionScope.islogin }">
				<a href="/member/myinfo">회원정보수정</a>
				<a href="/member/logout" onclick="logout()">로그아웃</a><br>
				</c:if>
				<c:if test="${empty sessionScope.islogin }">
				<a href="/member/login">로그인</a>
				</c:if>
			</div>
		</div>
		<div class="search-bar">
<!-- 			<input type="text" placeholder="검색..."> -->
<!-- 			검색기능을 제외하고 프로젝트 이름을 적자	 -->
			Project S
		</div>
	</div>

	<div class="content-wrapper" id="contentWrapper">
		<c:forEach var="content" items="${contentlist}">
		<div class="post">
			<div class="left-section">
				<div class="boardNo">
<%-- 				${content.boardNo } --%>
					${content.title }
				</div>
				<div class="profile-overlay">
					<img src="/resources/profile.png" alt="프로필" class=profile> <span class="username">
						${nickMap[content.boardNo]}<br>
						</span>
				</div>
				<!-- 파일 출력 부분 -->
                    <c:forEach var="file" items="${fileMap[content.boardNo]}">
<%--         					<img src="${file.path}/${file.stored}" alt="사진"> --%>
						<!-- 위 방식이 스프링 프레임워크에 의해 차단되어 우회를 해야함 -->	
<%-- 						<a href="./image?fileno=${file.fileno }">이미지</a> --%>
						<img src="/main/image?fileno=${file.fileno }">
    				</c:forEach>	
<!-- 				<img src="image_placeholder.jpg" alt="사진"> -->
					<div class="post-content">
					${content.content }
					</div>
				<div class="post-actions">
					<!-- 추천할때 테스트용으로 회원번호 2번으로 테스트 넣음 -->
<%-- 					<a href="./recommend?memberno=2&boardNo=${content.boardNo}"/> --%>
					<a href="javascript:void(0);" class="like-btn" data-boardno="${content.boardNo}">
					<div id="isRecommend_${content.boardNo}"><span>${recommendMap[content.boardNo] == 1 ? '❤️ 좋아요 취소' : '❤️ 좋아요'}</span></div></a>
                    <span>추천수</span> <div id="recommendNo_${content.boardNo}"><span>${numberofRecommend[content.boardNo] }</span></div>
					
				</div>
			</div>
			<div class="right-section">
			<!-- 댓글 부분은 외부 파일을 새롭게 import 해서 구현할 생각 - 좀더 확장성있고 유연한 구조가 될 것으로 판단한다 -->
			<!-- 개발이 어느정도 진행된 시점에서 외부파일 import 전략은 오히려 코드의 복잡성만 더하는 실패한 전략으로 결론나고 있다 -->
			<!-- 당장 AJAX 코드가 예상치못한 치명적인 버그로 인해 JS 코드를 view.jsp로 이관해왔다는 것부터가 이미 실패의 징조 -->
			<!-- 어찌저찌 구현에 성공했고 테스트 완료했음 -->
				<div id="viewComment_${content.boardNo }" class="comment"><c:import url="/main/viewcomment?memberno=${sessionScope.memberNo}&boardNo=${content.boardNo }"></c:import></div>
			</div>
		</div>
		</c:forEach>
	</div>

	<div class="bottom-menu">
	<i class="bi bi-house">홈</i>
		<a href="#">홈</a> 
		<i class="bi bi-chat-left-text">  <a href="javascript:void(0);" onclick="window.open('/socket/chat', 'chat', 'width=648, height=750')">채팅</a> </i>
		<a href="/main/upload">게시물 작성</a> 
		<a href="/main/mycontent">게시물 수정</a>
		<!-- 게시물 수정 페이지는 본인이 작성한 게시물을 최근 순으로 조회한 다음에 수정 페이지를 따로 두어 -->
		<!-- 게시물을 수정하는 인터페이스를 띄우고, 수정하면 업데이트 가능 -->
		<!-- 게시물 삭제는 게시물 수정 페이지에서 같이 구현 -->
<!-- 		<a href="#">설정</a> -->
	</div>

	<script>
        function toggleDropdown() {
            const dropdownMenu = document.getElementById('dropdownMenu');
            dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
        }

        function chat() {
            // window.location.href = '/main/main';
        	window.open('/socket/chat', 'chat', 'width=648', 'height=816');
        }

        document.addEventListener('click', function(event) {
            const dropdownMenu = document.getElementById('dropdownMenu');
            const userInfoContainer = document.querySelector('.user-info-container');
            
            if (!userInfoContainer.contains(event.target)) {
                dropdownMenu.style.display = 'none';
            }
        });

        const contentWrapper = document.getElementById('contentWrapper');
        const posts = document.querySelectorAll('.post');
        const postWidth = posts[0].offsetWidth + 60;
        
        // 현재 보여지는 게시물의 투명도를 업데이트하는 함수
        function updatePostOpacity() {
            posts.forEach(post => post.style.opacity = '0.3'); // 모든 게시물 투명도 낮춤
            const middlePostIndex = Math.round(contentWrapper.scrollLeft / postWidth); // 중간 게시물 찾기
            if (posts[middlePostIndex]) {
                posts[middlePostIndex].style.opacity = '1'; // 중간 게시물 불투명
            }
        }

        // 스크롤 및 드래그를 통해 투명도 업데이트
        contentWrapper.addEventListener('scroll', updatePostOpacity);
        updatePostOpacity();

        contentWrapper.addEventListener('wheel', (event) => {
            event.preventDefault();
            const direction = event.deltaY > 0 ? 1 : -1;
            contentWrapper.scrollBy({
                left: direction * postWidth,
                behavior: 'smooth'
            });
        });

        let isDragging = false;
        let startX;
        let scrollLeft;

        contentWrapper.addEventListener('mousedown', (e) => {
            isDragging = true;
            startX = e.pageX;
            scrollLeft = contentWrapper.scrollLeft;
            contentWrapper.style.cursor = 'grabbing';
        });

        contentWrapper.addEventListener('mouseup', () => {
            isDragging = false;
            contentWrapper.style.cursor = 'grab';
        });

        contentWrapper.addEventListener('mousemove', (e) => {
            if (!isDragging) return;
            e.preventDefault();
            const x = e.pageX;
            const walk = (x - startX) * 1.5; 
            contentWrapper.scrollLeft = scrollLeft - walk;
            updatePostOpacity(); // 드래그 중 투명도 업데이트
        });

        contentWrapper.addEventListener('mouseleave', () => {
            isDragging = false;
            contentWrapper.style.cursor = 'grab';
        });
        
        $(function() {
            // 추천 버튼 클릭시
            $(".like-btn").on("click", function() {
                var boardNo = $(this).data("boardno");
//                 var memberno = 2;  // 예시로 테스트용 사용자의 번호 2를 설정
                var memberno = <%=session.getAttribute("memberNo") %>;  // 더이상 테스트용 사용자번호를 쓰지 않고, 본격적으로 로그인 정보를 활용

                // AJAX 요청
                $.ajax({
                    type: "GET",
                    url: "./recommend",
                    data: {
                        memberno: memberno,
                        boardNo: boardNo,
                    },
                    dataType: "json",
                    success: function(response) {
                        console.log("AJAX 성공");
                        console.log(response);

                        // 추천 여부와 추천 수 업데이트
                        if(response.isRecommend == 0) {
                        	$("div#isRecommend_" + boardNo).html("<span>❤️ 좋아요</span>");
                        }
                        else {
                        	$("div#isRecommend_" + boardNo).html("<span>❤️ 좋아요 취소</span>");
                        }
            			//div#recommendNo_boardNo에 응답 데이터 반영하기
        				$("div#recommendNo_" + boardNo).html("<span>" + response.recommendno + "</span>");
                        
                    },
                    error: function() {
                        console.log("AJAX 실패");
                    }
                });
            });
            
        	//댓글 추천 버튼 클릭시
//         	$(".comlike-btn").on("click", function() {
        	
        	//댓글 리스트가 페이지 로드 시에 한 번만 렌더링되고, 이후 AJAX로 댓글을 추가하거나 삭제한다면,
        	//초기 로딩 시에만 이벤트 리스너가 적용되고 이후에는 적용되지 않기 때문에 이벤트 위임
        	//(event delegation) 방식으로 문제를 해결해야 한다고 함.
        	
        	//댓글 추천 버튼에 관련한 AJAX 구현이 import되는 viewcomment.jsp에서 구현했더니
        	//게시글 수만큼 AJAX가 중복해서 실행되는 문제가 발생하여 view.jsp로 AJAX 코드를
        	//이관해 왔음
        	// -> 게시글 수만큼 viewcomment.jsp가 중복 로드되면서 viewcomment.jsp에 있는
        	//AJAX 코드도 중복해서 생성되는 것이 원인으로 밝혀졌다.
        	$(document).on("click", ".comlike-btn", function() {
        		var commentno = $(this).data("commentno");
//         		var memberno = 2; //예시로 테스트용 사용자의 번호 2를 설정
        		var memberno = <%=session.getAttribute("memberNo") %>; // 더이상 테스트용 사용자번호를 쓰지 않고, 본격적으로 로그인 정보를 활용
        		// AJAX 요청
        		$.ajax({
        			type: "POST",
        			url: "./recommendcomment",
        			data: {
        				commentno: commentno,
        				memberno: memberno
        			},
        			dataType: "json",
        			success: function(resp) {
        				console.log("AJAX 성공");
        				console.log(resp);
        				
        				// 추천 수 업데이트
        				$("div#recommendcomm_" + commentno).html('<h3>' + resp.recommendno + '</h3>');
        	            // 추천 여부에 따라 버튼 상태 업데이트
        	            var recommendText = resp.isRecommend === 0 ? '추천' : '추천취소';
        	            var recommendClass = resp.isRecommend === 0 ? 'comlike-btn' : 'comlike-btn'; // 추천 상태에 따라 클래스 추가

        	            // 추천 여부를 변경한 div 내용 갱신
        	            $("div#isrecommendcomm_" + commentno).html('<h5><a href="javascript:void(0);" class="' + recommendClass + '" data-commentno="' + commentno + '">' + recommendText + '</a></h5>');
        			},
        			error: function() {
                        console.log("AJAX 실패");
        			}
        			
        		});
        	});
        	
        	//댓글 삭제할때
        	$(document).on("click", ".comdel-btn", function() {
                var boardNo = $(this).data("boardno");
        		var commentno = $(this).data("commentno");
//         		var memberno = 2; //예시로 테스트용 사용자의 번호 2를 설정
				var memberno = <%=session.getAttribute("memberNo") %>; // 더이상 테스트용 사용자번호를 쓰지 않고, 본격적으로 로그인 정보를 활용     	
        		// AJAX 요청
        		$.ajax({
        			type: "POST",
        			url: "./delcomment",
        			data: {
        				boardNo: boardNo,
        				commentno: commentno,
        				memberno: memberno
        			},
        			dataType: "json",
        			success: function(response) {
        				console.log("AJAX 성공");
        				console.log(response);
        				
        	            // 댓글 목록을 갱신하는 요청
        	            refreshComments(memberno, boardNo);
        				
        			},
        			error: function() {
                        console.log("AJAX 실패");
                        
                        // 댓글 목록을 갱신하는 요청
        	            refreshComments(memberno, boardNo);
        			}
        			
        		});
        		
        	
        	});
        	
        	//댓글 입력할때
        	//댓글을 입력하면 먼저 댓글을 서버로 전송하고, 이후 서버에서 댓글 뷰를 리다이렉트 처리
        	
        	
			$(document).off("click", "[class^='addcomm_']").on("click", "[class^='addcomm_']", function(event) {
    			event.preventDefault();  // 폼 제출 기본 동작을 막음

    			var buttonClass = $(this).attr("class");  // 클릭한 버튼의 class를 가져옴
    			console.log(buttonClass);  // console로 class 값을 확인

    			// 폼 필드에서 값 가져오기
    			var memberno = $("input[name='memberno']").val();  // memberno 값
    			var boardNo = buttonClass.split('_')[1];  // 'addcomm_${boardNo}' 형태에서 boardNo 추출
    			console.log("boardNo:", boardNo);  // boardNo가 제대로 추출되는지 확인

			    // boardNo 값이 제대로 추출되었는지 확인 후, upcomment 요소를 찾음
    			var upcomment = $('#upcomment_' + boardNo).val();  // .val()을 사용하여 입력 값 가져오기
    			console.log("upcomment:", upcomment);  // upcomment 값 확인

			    // 댓글이 비어 있는지 체크
				if (upcomment.trim() === "") {
	        		alert("댓글을 입력하세요!");
       			return;
			    }

			    // 댓글을 서버로 전송하는 AJAX 요청
			    $.ajax({
			        type: "POST",
			        url: "./uploadcomment",  // 댓글을 전송할 URL
			        data: {
			            memberno: memberno,
			            boardNo: boardNo,
			            upcomment: upcomment   // 댓글 내용
			        },
			        datatype: "json",  // 응답 데이터 형식
			        success: function(response) {
			            console.log("댓글 입력 성공");
			            console.log(response);
			
			            // 댓글 입력란 초기화
			            $("#upcomment_" + boardNo).val("");  // 댓글 입력 필드 초기화
			
			            // 댓글 목록을 갱신하는 요청
			            refreshComments(memberno, boardNo);
			        },
			        error: function() {
			            console.log("댓글 입력 실패");
			
			            // 댓글 목록을 갱신하는 요청
			            refreshComments(memberno, boardNo);
			        }
			    });
			});

        	// 댓글 목록을 갱신하는 함수, 나중에 댓글 삭제할때도 같은 함수가 호출될 것이다
        	function refreshComments(memberno, boardNo) {
        	    $.ajax({
        	        type: "GET",  // GET 방식으로 댓글 목록 가져옴
        	        url: "./viewcomment",  // 댓글 목록을 받을 URL
        	        data: {
        	            memberno: memberno,
        	            boardNo: boardNo
        	        },
        	        datatype: "html",  // 서버로부터 HTML 형식으로 댓글 목록 받음
        	        success: function(response) {
        	            console.log("댓글 목록 갱신 성공");
        	            // 댓글 목록을 해당 DOM에 갱신
        	            $("#viewComment_" + boardNo).html(response);  // #viewComment에 댓글 목록 업데이트
        	           
        	        },
        	        error: function() {
        	            console.log("댓글 목록 갱신 실패");
        	        }
        	    });
        	}
        	
          	// 댓글 페이지네이션 관련 코드
            // 페이지네이션 링크 클릭 시 AJAX 호출
//             $(".page-link").click(function(event) {}
            $(document).on('click', '.page-link', function(event) {
                event.preventDefault();  // 기본 링크 동작 막기
                
                
                var curPage = $(this).attr('data-curpage');  // 페이지 번호 추출
                var boardNo = $(this).data('boardno');  // 현재 게시물 번호
//                 var memberNo = 2;  // 현재 사용자 번호 (임시로 2 설정)
                var memberNo = <%=session.getAttribute("memberNo") %>;  // 현재 사용자 번호
                
                // AJAX 요청
                $.ajax({
                    url: './viewcomment',  // 댓글을 새로 불러오는 URL
                    type: 'GET',
                    data: {
                        boardNo: boardNo,
                        memberno: memberNo,
                        curPage: curPage  // 현재 페이지 번호
                    },
                    success: function(response) {
                        // 댓글 영역 갱신
                        $('#viewComment_' + boardNo).html(response);  // 새로운 댓글 데이터를 받아서 갱신
                    },
                    error: function() {
                        alert('댓글을 불러오는 데 오류가 발생했습니다.');
                    }
        		});
            });
		});
    </script>
</body>
</html>
