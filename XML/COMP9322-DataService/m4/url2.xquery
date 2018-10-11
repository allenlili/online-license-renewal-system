declare variable $ex_quarter as xs:string external;
declare variable $ex_postcode as xs:string external;
<table name="class-and-type">
    {
        for $row in doc("d_licence.xml")/table/row
        let $columns := $row/child::*
        where $row[child::column[@name = 'quarter']/text() eq $ex_quarter] and $row[child::column[@name = 'postcode']/text() eq $ex_postcode] 
        return <row>{$columns}</row>
    }
</table>