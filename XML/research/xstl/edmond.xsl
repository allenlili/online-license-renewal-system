<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:strip-space elements="*"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>
                    <xsl:value-of select="person/name/given"></xsl:value-of>
                </title>
            </head>
            <body>
                <table>
                    <tr>
                        <td>How old?</td>
                        <td><xsl:value-of select="person/age"/></td>
                    </tr>
                    <tr>
                        <td>No of Pets</td>
                        <td><xsl:value-of select="count(person/pets/child::node())"/></td>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>