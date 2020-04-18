<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<% int accId = (int) request.getAttribute("accountId");
		out.println("Account Id == " + accId);
		session.setAttribute("accountId", accId);
	%>
	<a href = "deposit.jsp">Deposit</a>
	<a href = "loggedIn/withdraw.jsp">Withdraw</a>
	<a href = "loggedIn/fundTransfer.jsp">Fund Transfer</a>
</body>
</html>