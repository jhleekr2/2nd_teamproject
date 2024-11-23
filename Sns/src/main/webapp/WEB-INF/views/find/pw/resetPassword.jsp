<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 변경</title>
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
        input[type="text"],
        input[type="tel"],
        input[type="password"] {
            width: calc(100% - 40px);
            padding: 15px;
            border: none;
            border-radius: 5px;
            background-color: #2A2C30;
            color: var(--text-color);
            font-size: 18px;
            margin-bottom: 15px;
            transition: all 0.3s ease;
        }
        
        input[readonly] {
            background-color: #2A2C30;
            color: var(--text-color);
            border: 1px solid #ccc;
        }

        input[type="password"]:focus,
        input[type="text"]:focus,
        input[type="tel"]:focus {
            background-color: #3a3f48;
            outline: none;
            border: 1px solid var(--button-bg-color);
        }

        input[type="password"] {
            /* 비밀번호 입력창 스타일 */
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
        .error-message {
            color: red;
            text-align: center;
            margin-bottom: 15px;
        }

        .password-requirements {
            font-size: 14px;
            color: #ccc;
            margin-top: 5px;
        }

        .password-requirements span {
            color: red;
        }
    </style>
</head>
<body>

<div class="header"></div>

<div class="find-account-container">
    <h2>비밀번호 변경</h2>

    <!-- 오류 메시지 표시 -->
    <c:if test="${not empty error}">
        <p class="error-message">${error}</p>
    </c:if>

    <!-- 이름과 전화번호를 받아서 비밀번호 재설정을 위한 폼 -->
    <form id="resetPasswordForm" action="${pageContext.request.contextPath}/find/pw/resetPassword" method="post" onsubmit="return validatePassword();">
        <!-- 이름과 전화번호는 읽기 전용 (편집 불가) -->
        <div class="input-group">
            <input type="text" id="name" name="name" placeholder="이름" value="${param.name}" readonly>
        </div>
        <div class="input-group">
            <input type="tel" id="phone" name="phone" placeholder="전화번호" value="${param.phone}" readonly>
        </div>
        <div class="input-container">
            <input type="password" id="newPassword" name="newPassword" placeholder="새 비밀번호" required>
            <div class="password-requirements">
                <span id="passwordError">비밀번호는 최소 8자, 대문자, 숫자 및 특수문자가 포함되어야 합니다.</span>
            </div>
        </div>
        <div class="input-container">
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="비밀번호 확인" required>
        </div>
        <input type="submit" value="비밀번호 재설정">
    </form>
</div>

<script>
    function validatePassword() {
        // 비밀번호 규칙
        const password = document.getElementById("newPassword").value;
        const confirmPassword = document.getElementById("confirmPassword").value;
        const passwordPattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;
        const passwordError = document.getElementById("passwordError");

        // 비밀번호 검증
        if (!password.match(passwordPattern)) {
            passwordError.style.display = "block";
            return false;
        } else {
            passwordError.style.display = "none";
        }

        // 비밀번호와 확인 비밀번호 일치 여부 확인
        if (password !== confirmPassword) {
            alert("비밀번호가 일치하지 않습니다.");
            return false;
        }

        return true;
    }
</script>

</body>
</html>
