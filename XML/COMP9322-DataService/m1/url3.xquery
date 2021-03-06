declare variable $ex_quarter as xs:string external;
<html>
    <header>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"/>
        <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js">#</script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js">#</script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js">#</script>
    </header>
    <body>
        <table class="table table-bordered table-hover table-sm">
        <thead>
            <tr class="thead-inverse">
                <th style="text-align:center;">Postcode</th>
                <th style="text-align:center;">Class C</th>
                <th style="text-align:center;">Class LR</th>
                <th style="text-align:center;">Class MR</th>
                <th style="text-align:center;">Class HR</th>
                <th style="text-align:center;">Class HC</th>
                <th style="text-align:center;">Class MC</th>
                <th style="text-align:center;">Class R</th>
                <th style="text-align:center;">Learner</th>
                <th style="text-align:center;">P1</th>
                <th style="text-align:center;">P2</th>
                <th style="text-align:center;">Unrestricted</th>
            </tr>
        </thead>
        <tbody>
        {
            for $postcode in doc("licence_q1.xml")/year/quarter[@name = $ex_quarter]/postcode
            order by xs:integer($postcode/class-and-type[@name = "Class C"]/text())
            return 
            <tr>
                <th>{data($postcode/@name)}</th>
                {
                    for $classType in $postcode/(class-and-type[@name = "Class C"] | class-and-type[@name = "Class LR"] | class-and-type[@name = "Learner"])
                    return 
                    <td>{$classType/text()}</td>
                }
            </tr>
        }
       </tbody>
        </table>
    </body>
</html>