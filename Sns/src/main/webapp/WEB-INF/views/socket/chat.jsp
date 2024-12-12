<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WebSocketTest - chat</title>
<!-- <link rel="stylesheet" href="https://me2.do/5BvBFJ57"> -->
<style>
html, body {
		padding: 0 !important;
		margin: 0 !important;
		background-color: #000 !important; 
		display: block;
		overflow: hidden;
	}
	
	body > div {
		margin: 0; 
		padding: 0; 
	}

	#main {
		width: 648px;
		height: 816px;
		margin: 3px;
		display: grid;
		grid-template-rows: repeat(12, 1fr);
		color: white;
	}
	
	#header {
		background-color: #FFBC42;
	}
	
	#header > h2 {		
		margin: 10px;
		margin-bottom: 10px;
		padding: 5px;
		text-align: center;
	}

	#list {
		border: 1px solid var(--border-color);
		box-sizing: content-box;
		padding: .5rem;
		grid-row-start: 2;
		grid-row-end: 12;
		font-size: 14px;
		overflow: auto;
	}
	
	#msg {
		margin-top: 3px;
		font-size: 16px;
	}
	
	#list .item {
		font-size: 14px;
		margin: 15px 0;
	}
	
	#list .item > div:first-child {
		display: flex;
	}
	
	#list .item.me > div:first-child {
		justify-content: flex-end;
	}
	
	#list .item.other > div:first-child {
		justify-content: flex-end;
		flex-direction: row-reverse;
	}
	
	#list .item > div:first-child > div:first-child {
		font-size: 16px;
		color: #777;
		margin: 3px 5px;
	}
	
	#list .item > div:first-child > div:nth-child(2) {
		border: 1px solid white;
		display: inline-block;
		min-width: 160px;
		max-width: 400px;
		text-align: left;
		padding: 3px 7px;
		font-size: 16px;
	}
	
	#list .state.item > div:first-child > div:nth-child(2) {
		background-color: rgb(255, 196, 0);
	}
	
	#list .item > div:last-child {
		font-size: 16px;
		color: #777;
		margin-top: 5px;
	}
	
	#list .me {
		text-align: right;
	}
	
	#list .other {
		text-align: left;
	}
	
	#list .msg.me.item > div:first-child > div:nth-child(2) {
		background-color: rgba(255, 99, 71, .8);
	}
	
	#list .msg.other.item > div:first-child > div:nth-child(2) {
		background-color: rgba(100, 149, 237, .8);
	}
	
	#list .msg img {
		width: 150px;
	}
</style>
</head>
<body>

<div id="main">
	<div id="header"><h2>채팅방</h2></div>
	<div id="list">
	
	</div>
	<c:if test="${sessionScope.islogin }">
	<input type="text" id="msg" placeholder="대화 내용을 입력하세요.">
	</c:if>
	<c:if test="${empty sessionScope.islogin }">
	<input type="text" id="msg" placeholder="로그인하세요." disabled="disabled">
	</c:if>
</div>

<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<script type="text/javascript">

	let name;
	let ws;
	const url = "ws://localhost:8088/socket/chatserver";
	//서버와의 연결 주소 - 서버에서는  @ServerEndpoint("/chatserver") 종단점을 설정해야함.
	
	$(document).ready(function() {
		var memberID = '<%= session.getAttribute("memberID") != null ? session.getAttribute("memberID") : "" %>';
		if (memberID !== "") {
			connect(memberID);
		}
	});
	
	function connect(name) {
		
		window.name = name;
		$('#header small').text(name);
		
		//서버와 연결하기 - 소켓 생성
		ws = new WebSocket(url);
		
		//클라이언트가 서버에 사용자 정보 전달
		ws.onopen = function(evt) {
			log('서버 연결 성공');
			
			//프로토콜을 이용한 메시지 보내기
			//사람이 들어온 상태 및 메시지도 ws.send로 전달
			//어떤 용도의 메시지인지 서버측에서는 알기 힘들기 때문에 메시지의 규칙으로 구분지어 전송
			//메시지 규칙으로 숫자는 상태코드를 나타내고, 그 상태코드에 따라 뒷 내용 구분지음
			
			//여기서는 메시지의 규칙은 JSON형식으로 정의하고
			//숫자로 표현된 상태코드를 사용하여 다양한 메시지 유형을 나타냄.
			
			//code: 상태코드
			// 1: 새로운 유저가 들어옴
			// 2: 기존 유저가 나감
			// 3: 메시지 전달
			// 4: 이모티콘 전달
			//sender: 보내는 유저명
			//receiver: 받는 유저명
			//content: 대화 내용
			//regdate: 날짜/시간
			
			let message = {
					code: '1',
					sender: window.name,
					receiver: '',
					content: '',
					regdate: new Date().toLocaleString()
			};
			
			//JSON 문자열로 변환 후 전송
			ws.send(JSON.stringify(message));
			print('', '대화방에 참여했습니다.', 'me', 'state', message.regdate);
			
			$('#msg').focus();
		};
		
		//서버에서 클라이언트에게 전달한 메시지
		ws.onmessage = function(evt) {
			let message = JSON.parse(evt.data);
			console.log(message);
			
			if (message.code == '1') {
				print('', `[\${message.sender}]님이 들어왔습니다.`, 'other', 'state', message.regdate);
			} else if (message.code == '2') {
				print('', `[\${message.sender}]님이 나갔습니다.`, 'other', 'state', message.regdate);
			} else if (message.code == '3') {
				print(message.sender, message.content, 'other', 'msg', message.regdate);
			}
		}
			
	}//connect

	function log(msg) {
		console.log(`[\${new Date().toLocaleTimeString()}] \${msg}`);
	}
	
	//대화창 출력 메소드
	function print(name, msg, side, state, time) {
		let temp = `
			<div class="item \${state} \${side}">
				<div>
					<div>\${name}</div>
					<div>\${msg}</div>
				</div>
				<div>\${time}</div>
			</div>`;
		
		$('#list').append(temp);
		
		//새로운 내용이 추가되면 스크롤을 바닥으로 내린다.
		scrollList();
	}
	
	function scrollList() {
		$('#list').scrollTop($('#list').outerHeight() + 300);
	}
	
	//beforeunload - 창이 닫히기 바로 직전에 발생하는 이벤트
	$(window).on('beforeunload', function() {
		disconnect();
	});
	
	function disconnect() {
		//대화방에서 나가면, 다른 사람들에게 안내메시지를 보낸다
		let message = {
				code: '2',
				sender: window.name,
				receiver: '',
				content: '',
				regdate: new Date().toLocaleString()
		};
		
		//JSON 문자열로 변환 후 전송
		ws.send(JSON.stringify(message));
	}
	
	//대화 스크롤 이벤트
	function scrollList() {
		$('#list').scrollTop($('#list').outerHeight() + 300);
	}
	
	$('#msg').keydown(function(evt) {
		//엔터를 눌렀을 때, 입력한 대화 내용을 서버로 전달하기
		if(evt.keyCode == 13) { //엔터를 눌렀을 때
			let message = {
				code: '3',
				sender: window.name,
				receiver: '',
				content: $('#msg').val(),
				regdate: new Date().toLocaleString()
			};
		
			ws.send(JSON.stringify(message));
			
			$('#msg').val('').focus();
			
			print(window.name, message.content, 'me', 'msg', message.regdate);
		}
	});
	
</script>

</body>
</html>