<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Online License Renewal System</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath }/lib/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath }/lib/css/scrolling-nav.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath }/lib/css/login.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath }/lib/css/table.css" rel="stylesheet">
  	
  	<style>
  	function showMe (box) {

    var chboxs = document.getElementsByName("c1");
    var vis = "none";
    for(var i=0;i<chboxs.length;i++) { 
        if(chboxs[i].checked){
         vis = "block";
            break;
        }
    }
    document.getElementById(box).style.display = vis;
	}
	</style>
  
  </head>

  <body id="page-top" >

    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
      <div class="container">
        <a class="navbar-brand js-scroll-trigger" href="#page-top">OLRS</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
          <c:if test="${ empty initiate }">
            <li class="nav-item">
              <a class="nav-link js-scroll-trigger" href="#initiate">Initiate</a>
            </li>
            </c:if>
            <li class="nav-item">
              <a class="nav-link js-scroll-trigger" href="#new_tasks">New Tasks</a>
            </li>
            <li class="nav-item">
              <a class="nav-link js-scroll-trigger" href="#pending_tasks">Pending Tasks</a>
            </li>
            <li class="nav-item">
              <a class="nav-link js-scroll-trigger" href="${pageContext.request.contextPath}/officer/logout">Logout</a>
            </li>
            
          </ul>
        </div>
      </div>
    </nav>
	
	<header class="">
      <center>
        <h1>Welcome to Online License Renewal System</h1>
       </center>
    </header>

	<c:if test="${ empty initiate }">
    <section id="initiate">
      <div class="container">
        <div class="row">
          <div class="col-lg-8 mx-auto">
            <%-- ${renewable_license} --%>

	         <table>
	          <thead>
			    <tr>
			      <th>Driver Name</th>
			      <th>License Number</th>
			      <th>License Class</th>
			      <th>Email</th>
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach items="${renewable_license}" var="list">
			   <tr>
			      <td><strong>
			      		<c:out value="${list.driverName}"></c:out></strong><br/> 
			      </td>
			      <td>
			      		<c:out value="${list.licenseNumber}"></c:out><br/> 
			      </td>
			      <td>
			      		<c:out value="${list.licenseClass}"></c:out><br/> 
			      </td>
			      <td>
			      		<c:out value="${list.email}"></c:out><br/> 
			      </td>
			   </tr>
			  </c:forEach> 
			  </tbody>
			</table>
          	<form id="initiate-form" class="" action="${pageContext.request.contextPath}/officer/notice/initiate" method="POST">
          		<button type="submit" class="btn btn-primary btn-block" value="">Initiate All</button>
          	</form>
          </div>
        </div>
      </div>
    </section>
	</c:if>
    

    <section id="new_tasks" class="bg-light">
      <div class="container">
        <div class="row">
          <div class="col-lg-8 mx-auto">
            <h2>New tasks</h2><br>
            
            <!-- Request manual validate task -->
            <h5>Require Manual Validation</h5>
            <c:if test="${not empty request_manual_validate }">
	          <table>
	          <thead>
			    <tr>
			      <th>ID</th><th>Address</th><th>Email</th><th>Status</th><th>Action</th>
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach items="${request_manual_validate}" var="list">
			   <tr>
			      <td><c:out value="${list.id}"></c:out><br/> </td>     
			      <td><c:out value="${list.tmpAddress}"></c:out><br/></td>
			      <td><c:out value="${list.tmpEmail}"></c:out><br/></td>
			      <td><c:out value="${list.status}"></c:out><br/></td>
			      <td>
			      	<form id="" class="" action="${pageContext.request.contextPath}/officer/notice/manual/assign_manual_validate" method="POST">
          				<input type="hidden" class="form-control" name="uuid" value="${list.uuid}"/>
          				<button type="submit" class="btn" value="">Start Task</button>
          			</form> 
			      </td>
			   </tr>
			  </c:forEach> 
			  </tbody>
			</table>
			</c:if>
			<c:if test="${ empty request_manual_validate }">
			<p>No License need manual validation.</p>
			</c:if>
			<br>
			
			<!-- Request manual extension task -->
          	<h5>Require Manual Extension</h5>
          	<c:if test="${not empty request_manual_extend }">
            <table>
	          <thead>
			    <tr>
			      <th>ID</th><th>Address</th><th>Email</th><th>Status</th><th>Action</th>
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach items="${request_manual_extend}" var="list">
			    <tr>
			      <td><c:out value="${list.id}"></c:out><br/> </td>     
			      <td><c:out value="${list.tmpAddress}"></c:out><br/></td>
			      <td><c:out value="${list.tmpEmail}"></c:out><br/></td>
			      <td><c:out value="${list.status}"></c:out><br/></td>
			      <td>
			      	<form id="" class="" action="${pageContext.request.contextPath}/officer/notice/manual/assign_manual_extend" method="POST">
           				<input type="hidden" class="form-control" name="uuid" value="${list.uuid}"/>
           				<button type="submit" class="btn" >Start Task</button>
					</form> 
			      </td>
			   </tr>
			  </c:forEach> 
			  </tbody>
			</table>
			</c:if>
			<c:if test="${ empty request_manual_extend }">
			<p>No License need manual validation.</p>
			</c:if>
          </div>
        </div>
      </div>
    </section>
    
    
    <section id="pending_tasks" >
      <div class="container">
        <div class="row">
          <div class="col-lg-8 mx-auto">
            <h2>Pending tasks</h2><br>
            
            <!-- Open manual validate task -->
            <h5>Require Manual Validation</h5>
            <c:if test="${not empty pending_manual_validate }">

	          <table>
	          <thead>
			    <tr>
			      <th>ID</th><th>Address</th><th>Email</th><th>Status</th><th colspan="2">Action</th>
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach items="${pending_manual_validate}" var="list">
			   <tr>
			      <td><c:out value="${list.id}"></c:out><br/> </td>     
			      <td><c:out value="${list.tmpAddress}"></c:out><br/></td>
			      <td><c:out value="${list.tmpEmail}"></c:out><br/></td>
			      <td><c:out value="${list.status}"></c:out><br/></td>
			      <td>
			      	<form id="" class="" action="${pageContext.request.contextPath}/officer/notice/manual/close_manual_validate" method="POST">
           				<input type="hidden" class="form-control"  name="uuid" value="${list.uuid}"/>
           				<button type="submit" class="btn btn-success" >Approve</button>
					</form> 
			      </td>
			      <td>
			      	<form id="" class="" action="${pageContext.request.contextPath}/officer/notice/manual/reject_manual_validate" method="POST">
           				<input type="hidden" class="form-control"  name="uuid" value="${list.uuid}"/>
           				<button type="submit" class="btn btn-danger" >Reject</button>
					</form> 
			      </td>
			   </tr>
			  </c:forEach> 
			  <c:if test="${ not empty enter_amount_manual_validate }">
			  <tr>
			  	<td>Update payment:</td>
			  	<form id="" class="" action="${pageContext.request.contextPath}/officer/notice/manual/update_amount" method="POST">
				 <td colspan="4">
				  	<input type="text" class="form-control" name="amount" placeholder="Amount" value="100" >
				  </td>
				  <td><button type="submit" class="btn" >Submit Amount</button></td>
				  </form>
			  </tr>
			  </c:if>
			  <c:if test="${ not empty enter_reject_validate_reason }">
			  <tr>
			  	<td>Rejection Reason:</td>
			  	<form id="" class="" action="${pageContext.request.contextPath}/officer/notice/manual/submit_reason_reject_manual" method="POST">
				 <td colspan="4">
				  	<input type="text" class="form-control" name="rejection_reason" placeholder="Invalide Information" value="Invalide Information">
				  </td>
				  <td><button type="submit" class="btn" >Submit Reject</button></td>
				  </form>
			  </tr>
			  </c:if>
			</table>
			</c:if>
			
			
			<!-- Open manual extension task -->
			<c:if test="${ empty pending_manual_validate }">
			<p>No pending manual validation tasks.</p>
			</c:if>
			<br>
          	<h5>Require Manual Extension</h5>
          	<c:if test="${not empty pending_manual_extend }">
	          <table>
	          <thead>
			    <tr>
			      <th>ID</th><th>Address</th><th>Email</th><th>Status</th><th colspan="2">Action</th>
			    </tr>
			  </thead>
			  <tbody>
			  <c:forEach items="${pending_manual_extend}" var="list">
			   <tr>
			      <td><c:out value="${list.id}"></c:out><br/> </td>     
			      <td><c:out value="${list.tmpAddress}"></c:out><br/></td>
			      <td><c:out value="${list.tmpEmail}"></c:out><br/></td>
			      <td><c:out value="${list.status}"></c:out><br/></td>
			      <td>
			      	<form id="" class="" action="${pageContext.request.contextPath}/officer/notice/manual/close_manual_extend" method="POST">
           				<input type="hidden" class="form-control"  name="uuid" value="${list.uuid}"/>
           				<button type="submit" class="btn btn-success" >Approve</button>
					</form> 
			      </td>
			      <td>
			      	<form id="" class="" action="${pageContext.request.contextPath}/officer/notice/manual/reject_manual_extend" method="POST">
           				<input type="hidden" class="form-control" name="uuid" value="${list.uuid}"/>
           				<button type="submit" class="btn btn-danger" >Reject</button>
					</form>
					 
			      </td>
			   </tr>
			  </c:forEach> 
			  <c:if test="${ not empty enter_amount_manual_extend }">
			  <tr>
			  	<td>Update payment:</td>
			  	<form id="" class="" action="${pageContext.request.contextPath}/officer/notice/manual/update_amount" method="POST">
				 <td colspan="4">
				  	<input type="text" class="form-control" name="amount" placeholder="Amount" value="100">
				  </td>
				  <td><button type="submit" class="btn" >Submit Amount</button></td>
				  </form>
			  </tr>
			  </c:if>
			  <c:if test="${ not empty enter_reject_extend_reason }">
			  <tr>
			  	<td>Rejection Reason:</td>
			  	<form id="" class="" action="${pageContext.request.contextPath}/officer/notice/manual/submit_reason_reject_manual" method="POST">
				 <td colspan="4">
				  	<input type="text" class="form-control" name="rejection_reason" placeholder="Invalide Information" value="Invalide Information">
				  </td>
				  <td><button type="submit" class="btn" >Submit Reject</button></td>
				  </form>
			  </tr>
			  </c:if>
			  </tbody>
			</table>
			</c:if>
			<c:if test="${ empty pending_manual_extend }">
			<p>No pending manual extension tasks.</p>
			</c:if>
         </div>
        </div>
      </div>
      
    </section>
   
	
    <!-- Footer -->
    <footer class="py-5 bg-dark">
      <div class="container">
        <p class="m-0 text-center text-white">Copyright &copy; Online License Renewal System 2017</p>
      </div>
      <!-- /.container -->
    </footer>

    <!-- Bootstrap core JavaScript -->
    <script src="${pageContext.request.contextPath }/lib/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/lib/vendor/popper/popper.min.js"></script>
    <script src="${pageContext.request.contextPath }/lib/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="${pageContext.request.contextPath }/lib/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom JavaScript for this theme -->
    <script src="${pageContext.request.contextPath }/lib/js/scrolling-nav.js"></script>
	<script src="${pageContext.request.contextPath }/lib/js/login.js"></script>
  </body>

</html>
