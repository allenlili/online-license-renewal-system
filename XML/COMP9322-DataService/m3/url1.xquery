declare variable $ex_quarter as xs:string external;
for $row in doc("c_licence.xml")/table/row
where $row/@quarter = $ex_quarter
return $row