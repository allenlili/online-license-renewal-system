<table name="class-and-type">
    {
        let $rows := for $row in doc("${dataPath}")/table/row
                     ${where}
                     return <row>${select}</row>
        for $row in $rows
        ${orderby}
        return $row
    }
</table>
