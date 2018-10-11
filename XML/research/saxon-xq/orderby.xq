<r>{
for $b in doc("bib.xml")//book
order by $b/title/text() ascending (:descending :)
return
  $b 
}</r>
