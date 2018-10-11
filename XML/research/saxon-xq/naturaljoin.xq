xquery version "1.0";
declare copy-namespaces no-preserve, inherit;


<r>{
for $book in doc("books.xml")//BOOKS/ITEM,
    $quote in doc("quotes.xml")//listing/ITEM
return 
 <book>
   {$book/TITLE, $quote/PRICE}
 </book>
} </r>
