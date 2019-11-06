<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/carrental">
		<html>
			<head><title>RENTALS</title></head>
			<body>
				<xsl:apply-templates select="rental"/>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="rental">
		<h1><xsl:value-of select="make"/></h1>
		<h2><xsl:value-of select="model"/></h2>
		<table border="0">
			<tr><td>nofdays:</td><td><xsl:value-of select="nofdays"/></td></tr>
			<tr><td>nofunits:</td><td><xsl:value-of select="nofunits"/></td></tr>
			<tr><td>discount:</td><td><xsl:value-of select="discount"/></td></tr>
		</table>
	</xsl:template>
</xsl:stylesheet>
