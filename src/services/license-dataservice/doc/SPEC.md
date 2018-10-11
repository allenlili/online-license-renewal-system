# COMP9322 Assignment2 Data Service
* NSW Car License Data Service (as a RESTful service)


### Requirement
* filter(<,<=, >=,  =, !=, eq, ne, gt, lt, ge, le)
* select multiple columns
* select columns, order by columns within select
* select columns, order by columns within select, filter
* output xml
* output Json
* output html


### Design


* Restful API
    * /class-and-type/<ouput format>?<query parameters>
    * <ouput format>: xml, html, json (default xml)
    * <query parameters>: &select, &filter, &orderby
    * HATEOAS, see the headers of each response
    
    
* Examples
    * &select
        * /class-and-type?&select=quarter, postcode
    * &filter
        * /class-and-type?&filter=quarter eq 1 and Class C < 300
    * &orderby
        * /class-and-type?&select=Class C,quarter,postcode&orderby=Class C, quarter, postcode
    * random combination of the above parameters
        * /class-and-type?&select=Class C,Class LR, Learner, Unrestricted&filter=quarter eq 2&orderby=Class C
    * view presentation format
        * /class-and-type?&select=Class C,Class LR, Learner, Unrestricted&filter=quarter eq 2&orderby=Class C
        * /class-and-type/xml?&select=Class C,Class LR, Learner, Unrestricted&filter=quarter eq 2&orderby=Class C
        * /class-and-type/html?&select=Class C,Class LR, Learner, Unrestricted&filter=quarter eq 2&orderby=Class C
        * /class-and-type/json?&select=Class C,Class LR, Learner, Unrestricted&filter=quarter eq 2&orderby=Class C
    * Note: orderby supports order of fields
    
    
### Implementation
* Ideas
    * follow OData framework style. http://www.odata.org/
    * XQuery ~ SQL
    * Data XML ~ Table
    * execute a piece of XQuery code from a template to generate the corresponding data format
    
   
* Business Logic 
     1. pull data online, save it
     2. parse html and obtain data offline into a single XML
     3. generate XQuery code
     4. execute XQuery code based on the criteria (filter, select, orderby, like SQL statements)
     5. generate query result in XML, json or html format
     
     
* Developer Tools
    * Java8 
    * Apache CXF: Java API for RESTful Web Services
    * Jaunt: Java Web Scraping & JSON Querying
    * JDom2: Java-based solution for accessing, manipulating, and outputting XML data from Java code
    * XQuery3: a query and functional programming language that queries and transforms collections of structured and unstructured data, usually in the form of XML
    * XQJ-API-1.0: a standard Java interface to XML DataSources which support XQuery 1.0
    * Freemarker: a java template engine
    * Boostrap4.0: a html/css/js library
      
      
* Note: see class diagram or sequence diagram


### Deploy && Run

* Method 1
* please put the war archive file to the local Apache Server.
* startup Server, run http://localhost:8080/COMP9322-NSWCL-DataService/(API-URL) on Browser, (API-URL) refers to Design part above


* Method 2
* import project into Eclipse to run it as well.

	

