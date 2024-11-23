<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #1F2124;
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background-color: #383A3F;
            width: 90%; /* 반응형으로 너비 설정 */
            max-width: 400px; /* 최대 너비 제한 */
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .container h1 {
            color: #F6B352;
            text-align: center;
            margin-bottom: 1.5rem;
            font-size: 1.8rem;
        }

        .form-control {
            background-color: #2A2C30;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 1rem;
            width: 100%;
            font-size: 1rem;
            margin-bottom: 1rem;
            box-sizing: border-box;
        }

        .form-group {
            display: flex;
            align-items: center;
            margin-bottom: 1rem;
        }

        .form-control-short {
            background-color: #2A2C30;
            color: white;
            border: none;
            border-radius: 5px 0 0 5px;
            padding: 1rem;
            font-size: 1rem;
            flex: 2;
            margin-right: 5px;
            box-sizing: border-box;
        }

        .btn-secondary {
            background-color: #505050;
            color: white;
            cursor: pointer;
            padding: 1rem;
            font-size: 0.9rem;
            border: none;
            border-radius: 0 5px 5px 0;
            flex: 1;
            transition: background-color 0.3s ease;
        }

        .btn-secondary:hover {
            background-color: #383838;
        }

        .btn-primary {
            background-color: #F6B352;
            font-size: 1rem;
            cursor: pointer;
            border: none;
            padding: 1rem;
            width: 100%;
            border-radius: 5px;
        }

        .btn-primary:hover {
            background-color: #e59c3d;
        }

        .btn-primary:disabled {
            background-color: #c5c5c5;
            cursor: not-allowed;
        }

        .error {
            color: red;
            font-size: 0.9rem;
            text-align: center;
            margin-bottom: 0.5rem;
        }

        .success {
            color: green;
            font-size: 0.9rem;
            text-align: center;
            margin-bottom: 0.5rem;
        }

        @media (max-width: 600px) {
            .container {
                width: 95%; /* 모바일 화면에서 너비 조정 */
                padding: 1.5rem;
            }

            .container h1 {
                font-size: 1.5rem;
            }

            .form-control, .form-control-short, .btn-secondary, .btn-primary {
                font-size: 0.9rem;
                padding: 0.8rem;
            }
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        let isIdDuplicate = false;

        function checkDuplicateId() {
            var memberId = document.getElementById("memberID").value;

            if (memberId.trim() === "") {
                alert("아이디를 입력해주세요.");
                return;
            }

            // Ajax 요청을 통해 서버에서 아이디 중복 체크 수행
            $.ajax({
                url: "${pageContext.request.contextPath}/member/checkId",
                type: "GET",
                data: { memberId: memberId },
                success: function(response) {
                    if (response === "duplicate") {
                        $("#id-check-result").text("중복된 아이디입니다.").removeClass("success").addClass("error");
                        isIdDuplicate = true;
                        $("#signupButton").prop("disabled", true);
                    } else {
                        $("#id-check-result").text("사용 가능한 아이디입니다.").removeClass("error").addClass("success");
                        isIdDuplicate = false;
                        $("#signupButton").prop("disabled", false);
                    }
                }
            });
        }

        function validatePassword() {
            var password = document.getElementById("memberPW").value;
            var confirmPassword = document.getElementById("confirmPW").value;
            var passwordPattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

            if (!passwordPattern.test(password)) {
                document.getElementById("error-message").innerText = "비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 각각 최소 하나 포함해야 합니다.";
                return false;
            }

            if (password !== confirmPassword) {
                document.getElementById("error-message").innerText = "비밀번호가 일치하지 않습니다.";
                return false;
            }

            document.getElementById("error-message").innerText = ""; // 오류 메시지 제거
            return true;
        }

        function validateForm(event) {
            event.preventDefault();
            const phone = $("#phone").val();

            $.ajax({
                url: "${pageContext.request.contextPath}/member/checkPhone",
                type: "GET",
                data: { phone: phone },
                success: function(isRegistered) {
                    if (isRegistered) {
                        alert("이미 가입된 번호입니다.");
                    } else if (!isIdDuplicate && validatePassword()) {
                        $("#signupForm")[0].submit();
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="container">
    <h1>회원가입</h1>
    <form id="signupForm" action="${pageContext.request.contextPath}/member/signup" method="post" onsubmit="validateForm(event);">
        <div id="error-message" class="error"></div>
        <input type="text" class="form-control" name="name" placeholder="이름" required>
        <input type="text" class="form-control" name="nick" placeholder="닉네임" required>
        <input type="text" class="form-control" id="phone" name="phone" placeholder="휴대폰 번호" maxlength="25" required>

        <div class="form-group">
            <input type="text" class="form-control-short" id="memberID" name="memberID" placeholder="아이디" required>
            <button type="button" class="btn-secondary" onclick="checkDuplicateId()">중복 체크</button>
        </div>
        <div id="id-check-result"></div>

        <input type="email" class="form-control" name="email" placeholder="이메일" required>
        <input type="password" class="form-control" id="memberPW" name="memberPW" placeholder="비밀번호" required>
        <input type="password" class="form-control" id="confirmPW" name="confirmPW" placeholder="비밀번호 확인" required>
        <input type="date" class="form-control" name="birthDate" required>
        <button type="submit" id="signupButton" class="btn btn-primary">회원가입</button>
    </form>
</div>
</body>
</html>