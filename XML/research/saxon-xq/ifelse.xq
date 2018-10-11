xquery version "1.0";

<r>{
for $b in doc("bib.xml")//book
return
<holding>
  { 
    $b/title,
    if ($b/@type = "Journal")
       then $b/editor
       else $b/author
  } 
</holding>
}</r>
