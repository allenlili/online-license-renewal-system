xquery version "1.0";
declare copy-namespaces no-preserve, inherit;


<r>{
for $book in doc("books.xml")//BOOKS/ITEM,
    $quote in doc("quotes.xml")//listing/ITEM
where $book/ISBN = $quote/ISBN
return 
 <book>
   {$book/TITLE, $quote/PRICE}
 </book>
} </r>
