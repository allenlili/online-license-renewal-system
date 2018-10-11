xquery version "1.0";

<r>{
for $b in doc("bib.xml")//book
where $b/publisher = "Addison-Wesley"
return
<book>
  { 
    $b/title,
    $b/author
  } 
</book>
}</r>
