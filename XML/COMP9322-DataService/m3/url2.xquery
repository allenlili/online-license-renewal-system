declare variable $ex_quarter as xs:string external;
declare variable $ex_postcode as xs:string external;
for $row in doc("c_licence.xml")/table/row
where $row/@quarter = $ex_quarter and $row/@postcode = $ex_postcode
return $row