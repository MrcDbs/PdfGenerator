<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/persona">
	 <xsl:processing-instruction name="xml-stylesheet"> 
   <xsl:text>type="text/xsl" href="formTemplate.xslt"</xsl:text> 
 </xsl:processing-instruction> 
	<html>
	
	<body>
		<h2>Form</h2>
	<form action="visualizza" method="post">
	    <xsl:value-of select="nome"/>: <input type="text" name="nome" size="20" required="true"/>
	    <br/>
	    <xsl:value-of select="cognome"/>: <input type="text" name="cognome" size="20" required="true"/>
	    <br/>
	    <xsl:value-of select="email"/>: <input type="email" name="email" pattern="[a-z0-9._]+@[a-z.-]+\.(com|org|it|net)$" required="true"/><label>Ex. "example@domain.com/.org/.it/.net"</label>
	    <br/>
	    <input type="submit" value="Visualizza" />
	</form>
	</body>
	</html>
	</xsl:template>
</xsl:stylesheet>