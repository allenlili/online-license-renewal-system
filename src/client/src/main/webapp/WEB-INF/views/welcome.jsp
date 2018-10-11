<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath }/lib/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath }/lib/css/scrolling-nav.css" rel="stylesheet">
    
    <!-- address / email validation input -->
	<link href="${pageContext.request.contextPath }/lib/css/driver.css" rel="stylesheet">
	
	<!-- Progress Bar -->
	<link rel="stylesheet" href="${pageContext.request.contextPath }/lib/css/style.css">			
 	<link href='https://fonts.googleapis.com/css?family=PT+Sans+Caption:400,700' rel='stylesheet' type='text/css'>
		
	
	<title>Online License Renewal System</title>
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
            <li class="nav-item">
              <a class="nav-link js-scroll-trigger" href="#info">Information</a>
            <li class="nav-item">
              <a class="nav-link js-scroll-trigger" href="#progress">Progress</a>
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

    <section id="info">
      <div class="container">
        <div class="row">
          <div class="col-lg-8 mx-auto">
          	
            <h2>Information</h2><br>
            <qq>
              <p>ID: ${driverNoticeInfoDto.uuid }</p>
             

<c:if test="${not empty driverNoticeInfoDto}">
			
			<c:if test="${driverNoticeInfoDto.status eq 'initial'}">
              	<p>Previous address: ${driverNoticeInfoDto.tmpAddress }</p>
              	<p>Previous email: ${driverNoticeInfoDto.tmpEmail }</p>
              	<!-- LOGIN FORM -->
				<div class="text-center" style="padding:50px 0">
					<div class="logo">Update Information</div>
					<!-- Main Form -->
					<div class="login-form-1">
						<form id="login-form" class="text-left" action="${pageContext.request.contextPath}/driver/notice/uuid/validate" onsubmit="return validate_form(this)" method="post">
							<div class="login-form-main-message"></div>
							<div class="main-login-form">
								<div class="login-group">
									<div class="form-group">
										<label for="preStreet" class="sr-only">Pre Street</label>
										<input type="text" class="form-control" id="preStreet" name="preStreet" placeholder="Pre Street" value="SHOP 1, 29">
									</div>
									<div class="form-group">
										<label for="streetName" class="sr-only">Street Name</label>
										<input type="text" class="form-control" id="streetName" name="streetName" placeholder="Street Name" value="CARINYA">
									</div>
									<div class="form-group">
										<label for="streetType" class="sr-only">Street Type</label>
										<input type="text" class="form-control" id="streetType" name="streetType" placeholder="Street Type" value="AVE">
									</div>
									<div class="form-group">
										<label for="suburb" class="sr-only">Suburb</label>
										<input type="text" class="form-control" id="suburb" name="suburb" placeholder="Suburb" value="ST MARYS">
									</div>
									<div class="form-group">
										<label for="state" class="sr-only">State</label>
										<input type="text" class="form-control" id="state" name="state" placeholder="State" value="NSW">
									</div>
									<div class="form-group">
										<label for="email" class="sr-only">Email</label>
										<input type="text" class="form-control" id="email" name="email" placeholder="Email" value="fangyijun1212@gmail.com">
									</div>
									
								</div>
								<button type="submit" class="login-button" value="Validate"><i class="fa fa-chevron-right"></i></button>
							</div>
							
						</form>
					</div>
					<!-- end:Main Form -->
				</div>
             </c:if>
             
             <c:if test="${driverNoticeInfoDto.status eq 'valid'}">
				<p>New address: ${driverNoticeInfoDto.tmpAddress }</p>
				<p>New Email: ${driverNoticeInfoDto.tmpEmail }</p>
				<p>We have validate your Address and Email successfully!</p>
				<form id="extent_license" class="" action="${pageContext.request.contextPath}/driver/notice/manual_extend" method="POST">
					<button type="submit" class="btn btn-primary btn-block" value="">Extend My License</button>
				</form>
				<br>
				<form id="extent_license" class="" action="${pageContext.request.contextPath}/driver/notice/not_extend" method="POST">
					<button type="submit" class="btn btn-primary btn-block" value="">Don't Extend My License</button>
				</form>
			</c:if>
			            
			<c:if test="${driverNoticeInfoDto.status eq 'invalid'}">
			<c:if test="${not empty invalide_reason }">
				<p>${invalide_reason }</p>
			</c:if>
				<p>Address: ${driverNoticeInfoDto.tmpAddress }</p>
	             <p>Email: ${driverNoticeInfoDto.tmpEmail }</p>
				<form id="initiate-form" class="" action="${pageContext.request.contextPath}/driver/notice/revalidation" method="POST">
					<button type="submit" class="btn btn-primary btn-block" value="">Modify My Address and Email</button>
				</form>
				<br>
				<form id="initiate-form" class="" action="${pageContext.request.contextPath}/driver/notice/manual_validate" method="POST">
					<button type="submit" class="btn btn-primary btn-block" value="">Request for Manual Validation</button>
				</form>
			</c:if>
			
			<c:if test="${driverNoticeInfoDto.status eq 'manual_validate'}">
				<p>Address: ${driverNoticeInfoDto.tmpAddress }</p>
	             <p>Email: ${driverNoticeInfoDto.tmpEmail }</p>
				<p>Your request for manual validation has been sent</p>
				<p>Please wait for a few days and check your progress on this page below</p>
				
			</c:if>
			
			<c:if test="${driverNoticeInfoDto.status eq 'manual_extend'}">
				<p>Address: ${driverNoticeInfoDto.tmpAddress }</p>
	             <p>Email: ${driverNoticeInfoDto.tmpEmail }</p>
				<p>Your request for extending your license has been sent</p>
				<p>Please wait for a few days and check your progress on this page below</p>
				
			</c:if>
			
			<c:if test="${driverNoticeInfoDto.status eq 'unpaid'}">
				<p>Address: ${driverNoticeInfoDto.tmpAddress }</p>
	             <p>Email: ${driverNoticeInfoDto.tmpEmail }</p>
				<p>Please review your progress and pay with below information</p>
				<c:if test="${driverNoticeInfoDto.isExtend eq 'true' }">
				<p>You have extend your license successfully.
				</c:if>
				<c:if test="${not empty driverPaymentInfoDto}">
					<p>Amount: $${driverPaymentInfoDto. amount}</p>
					
				 </c:if> 
				    <c:if test="${ empty showPaymentForm}">
					    <form id="initiate-form" class="" action="${pageContext.request.contextPath}/driver/notice/pay" method="POST">
							<button type="submit" class="btn btn-primary btn-block" value="">Link to pay</button>
						</form>
					</c:if>
					<c:if test="${not empty showPaymentForm}">
					<div class="text-center" style="padding:50px 0">
					<div class="logo">Payment Information</div>
					<div>
					Payment ID : ${driverPaymentInfoDto.id }<br>
					Amount : ${driverPaymentInfoDto.amount }<br>
					BSB : 123-456<br>
					Account Name : NSW RTA<br>
					Account Number : 12345678<br>
					</div>
					<!-- Bank Account Form -->
					<div class="login-form-1">
						<form id="login-form" class="text-left" action="${pageContext.request.contextPath}/driver/notice/complete" onsubmit="return validate_form(this)" method="post">
							<div class="login-form-main-message"></div>
							<div class="main-login-form">
								<div class="login-group">
									<div class="form-group">
										<label for="preStreet" class="sr-only">Account Name</label>
										<input type="text" class="form-control" name="acc_name" placeholder="Account name">
									</div>
									<div class="form-group">
										<label for="streetName" class="sr-only">Account Number</label>
										<input type="text" class="form-control" name="acc_number" placeholder="Account Number">
									</div>
									<div class="form-group">
										<label for="streetType" class="sr-only">BSB</label>
										<input type="text" class="form-control" name="bsb" placeholder="BSB" >
									</div>
									<div class="form-group">
										<label for="suburb" class="sr-only">Amount</label>
										<input type="text" class="form-control" name="amount" placeholder="Amount" value="${driverPaymentInfoDto.amount }">
									</div>
									
									
								</div>
								<button type="submit" class="login-button" value="Validate"><i class="fa fa-chevron-right"></i></button>
							</div>
							
						</form>
					</div>
					<!-- end:Main Form -->
				</div>
				</c:if>
			</c:if>
			
			<c:if test="${driverNoticeInfoDto.status eq 'rejected'}">
				<p>Your request has been rejected</p>
				<p>Rejection reason: ${driverNoticeInfoDto.rejectReason }</p>
				<p>Rejected by: Officer (ID: ${driverNoticeInfoDto.officerId })
				<form id="initiate-form" class="" action="${pageContext.request.contextPath}/driver/notice/finish" method="POST">
					<button type="submit" class="btn btn-primary btn-block" value="">Finish</button>
				</form>
			</c:if>
			
			<c:if test="${driverNoticeInfoDto.status eq 'paid'}">
				<p>Your have paid successfully.</p>
				
				<form id="initiate-form" class="" action="${pageContext.request.contextPath}/driver/notice/finish" method="POST">
					<button type="submit" class="btn btn-primary btn-block" value="">Finish</button>
				</form>
			</c:if>

		
</c:if>
</qq>
          </div>
        </div>
      </div>
    </section>

    

     <section id="progress" >
      <div class="container">
        <div class="row">
          <div class="col-lg-8 mx-auto"> 
            <h2>Progress</h2>
            <qq>Status: ${driverNoticeInfoDto.status }</qq>
         </div>
        </div>
      </div>   
            
            
	
<%-- <center>
<qq>License Renewal Status</qq>
</center> --%>
  <!--  To test add 'active' class and 'visited' class to different li elements -->
  
<div class="checkout-wrap">
  <ul class="checkout-bar">

    <li class="${ status.initial}">
      <a href="#info">Update Address & Email</a>
    </li>
    
    <li class="${ status.valid}">Address & Email Validation</li>
    
    <li class="${status.extension }">license Extension</li>
    
    <li class="${ status.payment}">Review & Payment</li>
    
    <li class="${ status.complete}">Complete</li>
       
  </ul>
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
	
	 <script type="text/javascript">

	 function validate_required(field,alerttxt){
	 with (field){
	   if (value==null||value=="")
	     {alert(alerttxt);return false}
	   else {return true}
	   }
	 }

	 function validate_form(thisform)
	 {
	 with (thisform){
		 if (validate_required(email,"Email must be filled out!")==false)
		     {email.focus();return false}
		   
		 if (validate_required(preStreet,"PreStreet must be filled out!")==false)
		     {preStreet.focus();return false}
		   
		 if (validate_required(streetName,"StreetName must be filled out!")==false)
		     {streetName.focus();return false}
		   
		 if (validate_required(streetType,"StreetType must be filled out!")==false)
		     {streetType.focus();return false}
		   
		  if (validate_required(suburb,"Suburb must be filled out!")==false)
		   {suburb.focus();return false}
		 
		 if (validate_required(state,"State must be filled out!")==false)
		 {state.focus();return false}
		}
	   
	 }
	</script>
  </body>

</html>
