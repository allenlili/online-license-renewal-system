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
${htmlSelect}
            </tr>
        </thead>
        <tbody>
        {
        
            let $rows := for $row in doc("${dataPath}")/table/row
                         ${where}
                         return <row>${select}</row>
            for $row in $rows
            ${orderby}
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
