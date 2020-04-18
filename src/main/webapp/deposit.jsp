<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div style="margin: 50px">
		<h2> Deposit...</h2>
		<%
			int accId = (int) session.getAttribute("accountId");
			out.println("Account Id == " + accId);
			request.setAttribute("accountId", accId);
		%>
		<form action="deposit">
			Enter Amount: <input type="text" name= "money"/><br>
			<input type="text" name="accountId" hidden="" value="<%= request.getAttribute("accountId")%>">
			<input type="submit" value="Deposit">
		</form>
	</div>
</body>
</html>