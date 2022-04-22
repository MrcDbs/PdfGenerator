<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<table>
		<jsp:useBean id="persona" scope="request" class="it.bean.Persona"/>
		<th>
			<td>Nome </td>
			<td>Cognome</td>
			<td>Email</td>
		</th>
		
		<tr>
			<td><jsp:getProperty property="nome" name="persona"/></td>
			<td><jsp:getProperty property="cognome" name="persona"/></td>
			<td><jsp:getProperty property="email" name="persona"/></td>
		</tr>
		
	</table>
	<input type="submit" value="Converti"/>
</body>
</html>