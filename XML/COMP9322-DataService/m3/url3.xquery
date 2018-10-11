declare variable $ex_quarter as xs:string external;
let $rows := for $row in doc("c_licence.xml")/table/row
                let $postcode := $row/child::column[@name = 'postcode']
                let $columns := $row/child::column[@name = 'Class C' or @name = 'Class LR' or @name = 'Learner' or @name = 'Unrestricted']
             where $row/@quarter = $ex_quarter
             return <row>{$postcode}{$columns}</row>
for $row in $rows
order by xs:integer($row/column[@name = 'Class C']/text())
return $row
