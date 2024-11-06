<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String theme = (String) session.getAttribute("theme");
    if (theme == null) {
        theme = "dark"; // 기본 테마를 dark로 설정
        session.setAttribute("theme", theme);
    }
%>
<style>
    /* 기본 테마 색상 설정 */
    :root {
        --background-color: <%= "dark".equals(theme) ? "#1F2124" : "#F5F5DC" %>;
        --text-color: <%= "dark".equals(theme) ? "white" : "black" %>;
        --container-bg-color: <%= "dark".equals(theme) ? "#383A3F" : "white" %>;
        --input-bg-color: <%= "dark".equals(theme) ? "#2A2C30" : "#F0F0F0" %>;
        --button-bg-color: <%= "dark".equals(theme) ? "#F6B352" : "#FFBC42" %>;
        --shadow-color: <%= "dark".equals(theme) ? "rgba(0, 0, 0, 0.2)" : "rgba(0, 0, 0, 0.15)" %>;
    }

    body {
        background-color: var(--background-color);
        color: var(--text-color);
    }

    .login-container, .post-container, .content-container {
        background-color: var(--container-bg-color);
        box-shadow: 0 4px 8px var(--shadow-color);
    }

    input[type="text"], input[type="password"] {
        background-color: var(--input-bg-color);`
        color: var(--text-color);
    }

    .button {
        background-color: var(--button-bg-color);
        color: var(--text-color);
    }
</style>
