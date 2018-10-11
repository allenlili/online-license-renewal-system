declare variable $ex_quarter as xs:string external;
<table name="class-and-type">
    {
        for $row in doc("d_licence.xml")/table/row
        let $columns := $row/child::*
        where $row[child::column[@name = 'quarter']/text() = $ex_quarter]
        return <row>{$columns}</row>
    }
</table>
