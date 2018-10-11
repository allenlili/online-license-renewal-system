package au.edu.unsw.comp9322.REST.constant;

public class Constant {
	public static final String LICENSE_SEQ_KEY = "license";
	public static final String NOTICE_SEQ_KEY = "notice";
	public static final String PAYMENT_SEQ_KEY = "payment";

	public static final String DOMAIN_NAME = "http://localhost:8080/ClientServer";

	public static final Object DRIVER_AUTHORIZATION_TOKEN = "driver";
	public static final Object OFFICER_AUTHORIZATION_TOKEN = "officer";

	// TODO put your email account
	public static final String EMAIL_USER = ""; // email address
	public static final String EMAIL_PASSWORD = ""; // email password

	// deprivative
	public static final String SOAP_WSDL = "http://192.168.99.100:8888/assignment1/EmployeeValidation?wsdl";

	/*
	 * 
	 * check errorCode ErrorCode of license
	 * 
	 * 511: license id not found
	 * 
	 * 512: can not update license before payment complete
	 * 
	 * 514: can not delete notice before payment complete
	 * 
	 * 513: query database of license error
	 *
	 */

	/*
	 * ErrorCode of notice
	 * 
	 * 521: notice id not found
	 * 
	 * 522: receive wrong notice input
	 * 
	 * 523: uuid not found
	 * 
	 * 524: job has already claimed
	 * 
	 * 525: change other's job
	 * 
	 * 526: query database of notice error
	 * 
	 * 527: can not validate when status is not initial
	 *
	 * 528: can not set manual validation when status is not invalid
	 * 
	 * 529: can not set manual extend when status is not valid
	 */

	/*
	 * ErrorCode of payment
	 * 
	 * 531: payment id not found
	 * 
	 * 532: receive wrong payment input
	 * 
	 * 533: query database of payment error
	 *
	 * 534: can not get payment when status is not valid
	 *
	 * 535: can not get payment when status is not unpaid
	 *
	 *
	 */

	/*
	 * ErrorCode of officer
	 * 
	 * 541: officer id not found
	 *
	 * 542: can not claim this job
	 *
	 * 543: you can not update others' job
	 *
	 * 544: you can not update unclaimed job
	 */
}
