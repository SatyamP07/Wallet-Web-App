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
		<h2> Set Passwords</h2>
		<form action="setPasswords" method="post">
			Set Account Password: <input type="text" name="accountPassword1"/><br>
			Confirm Account Password: <input type="text" name="accountPassword2"/><br><br>
			
			Set Transaction Password: <input type="text" name="transactionPassword1"/><br>
			Confirm Transaction Password: <input type="text" name="transactionPassword2"/><br>
			<input type="text" name="accountId" value ="<%= request.getAttribute("accountId") %>" hidden="true" />
			<input type="submit" value="Set Passwords">
		</form>
	</div>
</body>
</html>