<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>이메일로 계정 찾기</title>
    <style>
        :root {
            --background-color: #1F2124;
            --text-color: white;
            --container-bg-color: #383A3F;
            --button-bg-color: #F6B352;
            --shadow-color: rgba(0, 0, 0, 0.2);
        }

        body {
            margin: 0;
            padding: 0;
            background-color: var(--background-color);
            font-family: Arial, sans-serif;
            color: var(--text-color);
        }
        .header {
            width: 100%;
            height: 12vh;
            background-color: #FFBC42;
            box-shadow: 0 4px 8px var(--shadow-color);
        }
        .find-account-container {
            background-color: var(--container-bg-color);
            width: 30%;
            padding: 40px;
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            border-radius: 10px;
            box-shadow: 0 4px 8px var(--shadow-color);
        }
        .find-account-container h2 {
            color: var(--button-bg-color);
            text-align: center;
            margin-bottom: 30px;
            font-size: 28px;
        }
        .input-group {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }
        input[type="email"],
        input[type="text"] {
            width: calc(100% - 120px);
            padding: 15px;
            border: none;
            border-radius: 5px;
            background-color: #2A2C30;
            color: var(--text-color);
            font-size: 18px;
        }
        .send-code-btn {
            margin-left: 10px;
            background-color: var(--button-bg-color);
            color: white;
            border: none;
            padding: 5px;
            cursor: pointer;
            font-size: 16px;
            border-radius: 5px;
            height: 52px;
        }
        .send-code-btn:disabled {
            background-color: #888;
            cursor: not-allowed;
        }
        .timer {
            text-align: center;
            font-size: 20px;
            margin-top: 10px;
            color: var(--button-bg-color);
        }
        .input-container {
            margin-bottom: 20px;
        }
        input[type="submit"] {
            background-color: var(--button-bg-color);
            color: white;
            border: none;
            padding: 15px;
            font-size: 18px;
            cursor: pointer;
            border-radius: 5px;
            width: 100%;
            margin-top: 20px;
        }
        input[type="submit"]:hover {
            background-color: #e59c3d;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>

<div class="header"></div>

<div class="find-account-container">
    <h2>이메일로 계정 찾기</h2>
    <form id="findByEmailForm" action="${pageContext.request.contextPath}/find/id/findAccountByEmail" method="post">
        <div class="input-group">
            <input type="email" id="email" name="email" placeholder="이메일 주소" required oninput="toggleSendCodeButton()">
            <button type="button" class="send-code-btn" id="sendCodeButton" onclick="sendCode()" disabled>인증 번호</button>
        </div>
        <div id="timer" class="timer">05:00</div>
        <div class="input-container">
            <input type="text" id="verificationCode" name="verificationCode" placeholder="인증번호 입력" maxlength="6" required>
        </div>
        <input type="submit" value="계정 찾기" onclick="validateCode(event)">
    </form>
</div>

<script>
    let timerInterval;
    let isCodeSent = false;

    function toggleSendCodeButton() {
        const email = document.getElementById('email').value;
        const sendCodeButton = document.getElementById('sendCodeButton');
        sendCodeButton.disabled = email.trim() === '';
    }

    // 인증 번호 버튼을 클릭할 때 이메일을 확인
    function sendCode() {
        const email = document.getElementById('email').value;
        const sendCodeButton = document.getElementById('sendCodeButton');

        $.ajax({
            url: '${pageContext.request.contextPath}/find/checkEmail', 
            type: 'GET',
            data: { email: email },
            success: function(response) {
                if (response) {
                    if (!isCodeSent) {
                        startTimer(5 * 60);
                        sendCodeButton.textContent = '재발송';
                        isCodeSent = true;
                    }
                } else {
                    alert("해당 이메일로 등록된 계정이 없습니다.");
                }
            },
            error: function() {
                alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
            }
        });

    }

    function startTimer(duration) {
        clearInterval(timerInterval);
        let timer = duration;
        timerInterval = setInterval(function () {
            let minutes = Math.floor(timer / 60);
            let seconds = timer % 60;
            minutes = minutes < 10 ? '0' + minutes : minutes;
            seconds = seconds < 10 ? '0' + seconds : seconds;
            document.getElementById('timer').textContent = minutes + ':' + seconds;
            if (--timer < 0) {
                clearInterval(timerInterval);
                document.getElementById('timer').textContent = '00:00';
            }
        }, 1000);
    }

    function validateCode(event) {
        const code = document.getElementById('verificationCode').value;
        if (code.length !== 6) {
            event.preventDefault();
        }
    }
</script>

</body>
</html>