package au.edu.unsw.comp9322.CLIENT.constant;

public class Constant {
	/*
	 * Service Server URL
	 */
	public static final String URL = "http://localhost:8090/api";

	// TODO change to your docker URL
	/*
	 * docker-machine start comp9322srv
	 * 
	 * get SOAP_WSDL by:docker-machine ls
	 * 
	 * download assignment1.tar and cd to that folder
	 * 
	 * docker-machine scp assignment1.tar comp9322srv:
	 * 
	 * docker-machine ssh comp9322srv
	 * 
	 * docker load --input assignment1.tar
	 * 
	 * docker run -it --rm --name assignment1 -p 8888:8080 assignment1:latest
	 */
	public static final String SOAP_WSDL = "http://192.168.99.100:8888/assignment1/EmployeeValidation?wsdl";
	
	/*
	 * Mysql configuration
	 */
	public static final String MYSQL_PORT = "3306";
	public static final String MYSQL_DB_NAME = "OLRS";
	public static final String MYSQL_USERNAME = "root";
	public static final String MYSQL_PASSWORD = "";

}
