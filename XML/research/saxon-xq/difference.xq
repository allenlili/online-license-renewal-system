<r>{
  let $a := avg(doc("bib.xml")//book/price)
  for $b in doc("bib.xml")//book
  where $b/price > $a
  return
   <expensive_book>
     {$b/title}
     <price_difference>
       {$b/price - $a}
     </price_difference>
   </expensive_book>
}</r>    
