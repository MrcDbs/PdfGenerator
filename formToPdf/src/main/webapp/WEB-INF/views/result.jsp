<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"> -->
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<table>
		<jsp:useBean id="persona" scope="request" class="it.bean.Persona"/>
		<tr>
			<th>Nome </th>
			<th>Cognome</th>
			<th>Email</th>
		</tr>
		
		<tr>
		
			<td><jsp:getProperty property="nome" name="persona"/></td>
			<td><jsp:getProperty property="cognome" name="persona"/></td>
			<td><jsp:getProperty property="email" name="persona"/></td>
		</tr>
		
	</table>
	<form method="get" action="visualizza">
	<input type="hidden" name="pdf" value="visualizza"/>
<input value="Visualizza PDF" type="submit">
</form>
<!-- 	<form method="get" action="visualizza">
	<input type="hidden" name="pdf" value="genera"/>
<input value="Scarica PDF" type="submit">
</form> -->
</body>
</html>