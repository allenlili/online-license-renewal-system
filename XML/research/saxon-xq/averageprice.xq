<r>{
let $a := avg(doc("bib.xml")//book[publisher = $p]/price)
for $p in doc("bib.xml")//publisher
return 
   <publisher>
      <name> {$p/text()} </name>
      <avgprice> {$a} </avgprice>
   </publisher>
}</r>
