xquery version "1.0";

<r>{
for $b in doc("bib.xml")//book
let $a := $b/author
where contains($b/publisher,"Addison-Wesley")
return
<book>
  { 
    $b/title,
    <count>Number of authors {count($a)} </count>
  } 
</book>
}</r>
