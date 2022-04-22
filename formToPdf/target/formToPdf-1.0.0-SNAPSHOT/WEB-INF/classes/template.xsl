<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/persona">
	<xsl:processing-instruction name="xml-stylesheet">
  <xsl:text>type="text/xsl" href="client.xsl"</xsl:text>
</xsl:processing-instruction>
	<html>
	  <body>
	  
	  <table border="1">
	    <tr bgcolor="#9acd32">
	      <th>Nome</th>
	      <th>Cognome</th>
	      <th>Email</th>
	    </tr>
	    
	    <tr>
	      <td><xsl:value-of select="nome"/></td>
	      <td><xsl:value-of select="cognome"/></td>
	      <td><xsl:value-of select="email"/></td>
	    </tr> 
	    
	  </table>
	  <form action="visualizza" method="get">
	  <input type="submit" value="Genera PDF"/>
	  </form>
	  </body>
  </html>
	</xsl:template>
</xsl:stylesheet>