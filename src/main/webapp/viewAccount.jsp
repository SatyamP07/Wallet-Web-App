<%@page import="com.wallet.app.models.CustomerDetails"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%CustomerDetails customer = (CustomerDetails)request.getAttribute("customer");%>
	Name: <%= customer.getName() %>
	E-Mail: <%= customer.geteMail() %>
	Mobile: <%= customer.getMobileNumber() %>
	Balance: <%= customer.getBalance() %>
</body>
</html>