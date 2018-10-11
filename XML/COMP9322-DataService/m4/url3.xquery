declare variable $ex_quarter as xs:string external;
<table name="class-and-type">
    {
        let $rows := for $row in doc("d_licence.xml")/table/row
                     (:let $columns := $row/child::*:)
                     let $columns := $row/child::column[@name = 'Class C' or @name = 'Class LR' or @name = 'Learner' or @name = 'Unrestricted']
                     where $row[child::column[@name = 'quarter']/text() = $ex_quarter]
                     return <row>{$columns}</row>
        for $row in $rows
        order by xs:integer($row/column[@name = 'Class C']/text())
        return $row
    }
</table>
