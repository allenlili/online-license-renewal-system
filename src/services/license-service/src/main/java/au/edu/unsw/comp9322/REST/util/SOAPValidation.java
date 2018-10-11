package au.edu.unsw.comp9322.REST.util;

import java.net.URL;

import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.REST.constant.Constant;
import comp9322.assignment1.CheckAddressRequest;
import comp9322.assignment1.CheckAddressResponse;
import comp9322.assignment1.CheckEmailAddressRequest;
import comp9322.assignment1.CheckEmailAddressResponse;
import comp9322.assignment1.EmployeeValidationService;
import comp9322.assignment1.EmployeeValidationServiceImplService;
import comp9322.assignment1.ObjectFactory;
import comp9322.assignment1.ReturnPostcodeResponse;

@Service
public class SOAPValidation {

	static ObjectFactory objectFactory = new ObjectFactory();

	public String checkAddress(String preStreet, String streetName, String streetType, String suburb, String state) {

		String url_assignment1 = Constant.SOAP_WSDL;

		try {
			EmployeeValidationServiceImplService employeeValidatingServiceImplService = new EmployeeValidationServiceImplService(
					new URL(url_assignment1));
			EmployeeValidationService employeeValidatingService = employeeValidatingServiceImplService
					.getEmployeeValidationServiceImplPort();

			// validate address
			CheckAddressRequest checkAddressRequest = objectFactory.createCheckAddressRequest();
			checkAddressRequest.setPreStreet(preStreet);
			checkAddressRequest.setStreetName(streetName);
			checkAddressRequest.setStreetType(streetType);
			checkAddressRequest.setSuburb(suburb);
			checkAddressRequest.setState(state);

			String fullAddress = null;

			// check address
			CheckAddressResponse checkAddressResponse;

			checkAddressResponse = employeeValidatingService.checkAddress(checkAddressRequest);

			// address valid
			System.out.println("address valid");

			fullAddress = checkAddressResponse.getExactAddress();
			System.out.println(fullAddress);

			// return postcode
			comp9322.assignment1.ReturnPostcodeRequest returnPostcodeRequest = objectFactory
					.createReturnPostcodeRequest();
			returnPostcodeRequest.setSuburb(suburb);
			returnPostcodeRequest.setState(state);

			String postcodeOutput = null;
			ReturnPostcodeResponse returnPostcodeResponse = employeeValidatingService
					.returnPostcode(returnPostcodeRequest);
			postcodeOutput = returnPostcodeResponse.getPostcode();

			System.out.println(postcodeOutput);
			return fullAddress;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("address invalid");
			return null;
		}

	}

	public boolean checkEmail(String email) {
		try {
			String url_assignment1 = Constant.SOAP_WSDL;
			// System.out.println("EmailValidation Servlet");

			EmployeeValidationServiceImplService employeeValidatingServiceImplService = new EmployeeValidationServiceImplService(
					new URL(url_assignment1));
			EmployeeValidationService employeeValidatingService = employeeValidatingServiceImplService
					.getEmployeeValidationServiceImplPort();

			// validate email
			CheckEmailAddressRequest checkEmailAddressRequest = objectFactory.createCheckEmailAddressRequest();
			checkEmailAddressRequest.setEmail(email);

			CheckEmailAddressResponse checkEmailAddressResponse = employeeValidatingService
					.checkEmailAddress(checkEmailAddressRequest);

			if (checkEmailAddressResponse.isValue()) {
				// email valid
				System.out.println("email valid");
				return true;
			} else {
				// email invalid
				System.out.println("email invalid");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
