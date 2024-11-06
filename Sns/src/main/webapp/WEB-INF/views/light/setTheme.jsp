<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String theme = request.getParameter("theme");
    if (theme != null && (theme.equals("dark") || theme.equals("light"))) {
        session.setAttribute("theme", theme);
    }
%>
