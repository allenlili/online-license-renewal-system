<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"/>
        <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js">#</script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js">#</script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js">#</script>
    </head>
    <body>
        <table class="table table-bordered table-hover table-sm">
        <thead>
            <tr class="thead-inverse">
				<th style="text-align:center;">Class C</th>
				<th style="text-align:center;">quarter</th>
				<th style="text-align:center;">postcode</th>
            </tr>
        </thead>
        <tbody>
        {
        
            let $rows := for $row in doc("/Users/allen/Dropbox/Postgraduate/Study/Courses/17S2/COMP9322/Assignments/ass02/XML/COMP9322-DataService/m4/d_licence.xml")/table/row
(:                         let $columns := $row/child::column[@name = 'Class C' or @name = 'quarter' or @name = 'postcode']:)
(:                         {$columns/column[@name = 'Class C']}{$columns/column[@name = 'quarter']}{$columns/column[@name = 'postcode']}:)
(:                         return <row>{$row/child::column[@name = 'Class C']}{$row/child::column[@name = 'quarter']}{$row/child::column[@name = 'postcode']}</row>:)
                         return <row>{$row/child::*}</row>
            for $row in $rows
            order by xs:integer($row/column[@name = 'Class C']/text()), $row/column[@name = 'quarter']/text(), $row/column[@name = 'postcode']/text()
            return
            <tr>
                    {
                        for $column in $row/column
                        return 
                        <td style="text-align:right;">{$column/text()}</td>
                    }
            </tr>
        }
        </tbody>
        </table>
    </body>
</html>
