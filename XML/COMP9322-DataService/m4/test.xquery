<table name="class-and-type">
    {
        let $rows := for $row in doc("d_licence.xml")/table/row
                     let $columns := $row/child::column[@name = 'Class C' or @name = 'Learner']
                     where $row[child::column[@name = 'quarter']/text() eq 'q1'] and $row[xs:integer(child::column[@name = 'Class C']/text()) > 300]
                     return <row>{$columns}</row>
        for $row in $rows
        order by xs:integer($row/column[@name = 'Class C']/text())
        return $row
    }
</table>
