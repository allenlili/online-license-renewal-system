package au.edu.unsw.comp9322.CLIENT.controller;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.comp9322.CLIENT.bean.AddressAndEmailDto;
import au.edu.unsw.comp9322.CLIENT.bean.Driver;
import au.edu.unsw.comp9322.CLIENT.constant.Constant;
import au.edu.unsw.comp9322.CLIENT.dao.NoticeDao;
import au.edu.unsw.comp9322.CLIENT.responseModel.LicenseDto;
import au.edu.unsw.comp9322.CLIENT.responseModel.NoticeDto;
import au.edu.unsw.comp9322.CLIENT.responseModel.PaymentDto;
import au.edu.unsw.comp9322.CLIENT.service.impl.DriverNoticeServiceImpl;
import au.edu.unsw.comp9322.CLIENT.service.impl.StatusServiceImpl;
import au.edu.unsw.comp9322.CLIENT.util.AuthorizationUtil;
import au.edu.unsw.comp9322.CLIENT.util.HttpUtil;
import au.edu.unsw.comp9322.CLIENT.util.ModelUtil;
import au.edu.unsw.comp9322.CLIENT.util.SOAPValidation;

@Controller
@RequestMapping("/driver/notice")
public class DriverNoticeController {
	private Logger logger = Logger.getLogger("OLRS Logger");
	private String REST_URI = Constant.URL + "/notice";

	@Autowired
	HttpUtil httpUtil;

	@Autowired
	AuthorizationUtil authorizationUtil;

	@Autowired
	AddressAndEmailDto addressAndEmailDto;

	@Autowired
	ModelUtil modelUtil;

	@Autowired
	DriverNoticeServiceImpl driverNoticeServiceImpl;

	@Autowired
	StatusServiceImpl statusServiceImpl;

	@Autowired
	SOAPValidation soapValidation;

	@Autowired
	NoticeDao noticeDao;

	/**
	 * Driver start review here
	 * 
	 * @user:driver
	 * @Workflow 2
	 * @Click http://localhost:8080/ClientServer/notice/uuid/2485ba1e-b075-4ba5-a56c-ee7e19e34d1d
	 * @Call http://localhost:8090/api/notice/uuid/{uuid}
	 * @param notice_id
	 * @return NoticeDto of one driver's intormation
	 * @throws IOException
	 */
	@RequestMapping(value = "/uuid/{uuid}", produces = MediaType.APPLICATION_JSON)
	public ModelAndView notice_start_by_driver(@PathVariable("uuid") UUID uuid, HttpSession session, SessionStatus status)
			throws IOException {
		logger.info("Review process by driver");

		Driver driver = new Driver(uuid);
		authorizationUtil.setAuthorization("driver", driver, session);

		String URI = REST_URI + "/uuid/" + uuid;
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "driver");

		// get all info about this driver
		NoticeDto noticeDto = (NoticeDto) httpUtil.doHttpGet(URI, headers, NoticeDto.class);
		
		if(noticeDto.getErrorCode()!= null && noticeDto.getErrorCode().equals("523")) {
			System.out.println("enter here!!!!!!!!!!!!!!");
			ModelAndView modelAndView = new ModelAndView("invalid");
			return modelAndView;
		}
		
		session.setAttribute("driverNoticeInfoDto", noticeDto);
		statusServiceImpl.updateStatus(session);
		driverNoticeServiceImpl.putPaymentDtoIntoSession(session);
		
		
		ModelAndView modelAndView = new ModelAndView("welcome");
		return modelAndView;
	}

	@POST
	@RequestMapping(value = "/uuid/validate")
	public void validateAddressEmailSOAP(HttpServletResponse response, HttpSession session, String preStreet, String streetName,
			String streetType, String suburb, String state, String email) {
		logger.info("Validate Address and Email");
		addressAndEmailDto = new AddressAndEmailDto(preStreet, streetName, streetType, suburb, state, email);
		NoticeDto noticeDto = (NoticeDto) session.getAttribute("driverNoticeInfoDto");
		
		if (preStreet != null || streetName != null || streetType != null || suburb != null || state != null
				|| email != null) {
			
			System.out.println("SOAP VALIDATE");
			Boolean isValidAddress = null;
			Boolean isEmail = null;
			String returnValidAddress = null;
			// check address from SOAP
			returnValidAddress = soapValidation.checkAddress(preStreet, streetName, streetType, suburb, state);
			if (returnValidAddress != null) {
				isValidAddress = true;
				noticeDto.setTmpAddress(returnValidAddress); // get valid address from SOAP(SOAP format)
			} else {
				noticeDto.setTmpAddress(preStreet + " " + streetName + " " + streetType + ", " + suburb + ", " + state);
				isValidAddress = false;
			}
			// check email from SOAP
			if (soapValidation.checkEmail(email)) {
				isEmail = true;
			} else {
				isEmail = false;
			}
			noticeDto.setTmpEmail(email);

			// update notice status to "valid or invalid"
			if (isValidAddress && isEmail) {
				noticeDto.setStatus("valid");
			} else {
				noticeDto.setStatus("invalid");
			}

			if (isValidAddress && isEmail) {
				System.out.println("Email and Address Validate!!!");
			} else if (!isValidAddress && !isEmail) {
				System.out.println("Address and Email are invalid");
				session.setAttribute("invalide_reason", "Address and Email are invalid.");
			} else if (!isValidAddress) {
				System.out.println("Address is invalid");
				session.setAttribute("invalide_reason", "Address is invalid");
			} else if (!isEmail) {
				System.out.println("email is invalid");
				session.setAttribute("invalide_reason", "Email is invalid");
			}

			// update session
//			noticeDto = driverNoticeServiceImpl.updateNoticeDtoAddressEmail(session, noticeDto, addressAndEmailDto);
			System.out.println("After update:\n" + noticeDto.toString());
			session.setAttribute("driverNoticeInfoDto", noticeDto);
			statusServiceImpl.updateStatus(session);

			// update service side db
			Boolean statusCode = driverNoticeServiceImpl.driverUpdateNotice(session);
			// should be success
		}
		try {
			response.sendRedirect("/ClientServer/driver/notice/uuid/"+ noticeDto.getUuid());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@POST
 	@RequestMapping(value = "/uuid/UNSOAPvalidate")
	public void validateAddressEmail( HttpServletResponse response, HttpSession session, String preStreet, String streetName,String streetType,String suburb,String state,String email) {
		logger.info("");
		addressAndEmailDto = new AddressAndEmailDto(preStreet, streetName, streetType, suburb, state, email);
 		NoticeDto noticeDto = (NoticeDto) session.getAttribute("driverNoticeInfoDto");
 		if(preStreet!= null || streetName != null|| streetType != null|| suburb != null || state != null|| email != null) {
 			
			
			//TODO: Call validation here
			boolean result = false;//remove this line later, this is only for my debug
			
			if(result) {
 				System.out.println("Email and Address Validate!!!");
				noticeDto.setStatus("valid");			
			}else {		
				System.out.println("Email and Address Failed!!!");
				noticeDto.setStatus("invalid");
 			}
			
			//update session
 			noticeDto = driverNoticeServiceImpl.updateNoticeDtoAddressEmail(session, noticeDto, addressAndEmailDto);
			System.out.println("After update:\n"+noticeDto.toString());	
 			session.setAttribute("driverNoticeInfoDto", noticeDto);
 			statusServiceImpl.updateStatus(session);
			
			//update service side db
 			Boolean statusCode = driverNoticeServiceImpl.driverUpdateNotice(session);
			//should be success
 		}

		try {
			response.sendRedirect("/ClientServer/driver/notice/uuid/"+ noticeDto.getUuid());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
	
	@RequestMapping(value = "/revalidation")
	public void reenterAddressEmail( HttpServletResponse response,HttpSession session) {
		logger.info("Revaludate");
		NoticeDto noticeDto = (NoticeDto) session.getAttribute("driverNoticeInfoDto");
		System.out.println("Email and Address revalidation!!!");

		// update session
		noticeDto.setStatus("initial");
		session.setAttribute("driverNoticeInfoDto", noticeDto);
		statusServiceImpl.updateStatus(session);
		// update service database
		Boolean statusCode = driverNoticeServiceImpl.driverUpdateNotice(session);


		try {
			response.sendRedirect("/ClientServer/driver/notice/uuid/"+ noticeDto.getUuid());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/manual_validate")
	public void setManualValidation( HttpServletResponse response,HttpSession session) {
		logger.info("Request for Manual Validate");
		NoticeDto noticeDto = (NoticeDto) session.getAttribute("driverNoticeInfoDto");

		// update session
		noticeDto.setStatus("manual_validate");
		session.setAttribute("driverNoticeInfoDto", noticeDto);
		statusServiceImpl.updateStatus(session);
		// update service database
		Boolean statusCode = driverNoticeServiceImpl.driverUpdateNotice(session);


		try {
			response.sendRedirect("/ClientServer/driver/notice/uuid/"+ noticeDto.getUuid());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/manual_extend")
	public void licenseExtend( HttpServletResponse response,HttpSession session) {
		logger.info("Request for Manual Extend");
		NoticeDto noticeDto = (NoticeDto) session.getAttribute("driverNoticeInfoDto");

		// update session
		noticeDto.setStatus("manual_extend");
		noticeDto.setIsExtend("extend");
		session.setAttribute("driverNoticeInfoDto", noticeDto);
		statusServiceImpl.updateStatus(session);
		// update service database
		Boolean statusCode = driverNoticeServiceImpl.driverUpdateNotice(session);


		try {
			response.sendRedirect("/ClientServer/driver/notice/uuid/"+ noticeDto.getUuid());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/not_extend")
	public void licenseNotExtend( HttpServletResponse response,HttpSession session) {
		logger.info("Do not extend");
		NoticeDto noticeDto = (NoticeDto) session.getAttribute("driverNoticeInfoDto");

		// update session
		noticeDto.setStatus("unpaid");
		session.setAttribute("driverNoticeInfoDto", noticeDto);
		statusServiceImpl.updateStatus(session);
		
		// update service database: http
		String URI = REST_URI + "/uuid/" + noticeDto.getUuid()+"/not_extend";
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "driver");
		PaymentDto paymentDto= (PaymentDto) httpUtil.doHttpPutEmpty(URI, headers, PaymentDto.class);
		System.out.println(noticeDto.toString());
		session.setAttribute("driverPaymentInfoDto", paymentDto);
		
		try {
			response.sendRedirect("/ClientServer/driver/notice/uuid/"+ noticeDto.getUuid());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@POST
	@RequestMapping(value = "/pay")
	public void licensePay( HttpServletResponse response,HttpSession session) {
		logger.info("Pay for notice");
		NoticeDto noticeDto = (NoticeDto) session.getAttribute("driverNoticeInfoDto");

		// update session
		//noticeDto.setStatus("unpaid");
//		session.setAttribute("driverNoticeInfoDto", noticeDto);
//		statusServiceImpl.updateStatus(session);
		
		// update service database: http
		String URI = REST_URI + "/uuid/" + noticeDto.getUuid();
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "driver");
		
		PaymentDto paymentDto= (PaymentDto) httpUtil.doHttpGet(URI, headers, PaymentDto.class);
		System.out.println(noticeDto.toString());
		session.setAttribute("showPaymentForm", paymentDto);
		
		try {
			response.sendRedirect("/ClientServer/driver/notice/uuid/"+ noticeDto.getUuid());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@POST
	@RequestMapping(value = "/complete")
	public void sendPayment( HttpServletResponse response,HttpSession session) {
		logger.info("Complete and pay");
		NoticeDto noticeDto = (NoticeDto) session.getAttribute("driverNoticeInfoDto");
		PaymentDto paymentDto= (PaymentDto)session.getAttribute("driverPaymentInfoDto");


		String URI = Constant.URL + "/payment/"+paymentDto.getId()+"/paid";
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "driver");

		// get all info about this driver
		LicenseDto licenseDto = (LicenseDto) httpUtil.doHttpPutEmpty(URI, headers, LicenseDto.class);
		session.setAttribute("driverLicenseInfoDto", licenseDto);
		
		statusServiceImpl.updateStatus(session);
		
		noticeDao.sendStatus(noticeDto.getId(),"paid");
		try {
			response.sendRedirect("/ClientServer/driver/notice/uuid/"+ noticeDto.getUuid());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@POST
	@RequestMapping(value = "/finish")
	public ModelAndView finishNotice( HttpServletResponse response,HttpSession session) {
		logger.info("Finish renewal");
		// ../api/notice/uuid/{uuid}/archive DELETE
		NoticeDto noticeDto = (NoticeDto) session.getAttribute("driverNoticeInfoDto");

		String URI = REST_URI + "/uuid/"+noticeDto.getUuid()+"/archive";
//		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "driver");
//
//		// get all info about this driver
		httpUtil.doHttpDelete(URI, headers, LicenseDto.class);
		
		noticeDao.sendStatus(noticeDto.getId(),"archive");
		statusServiceImpl.updateStatus(session);
		session.invalidate();
		ModelAndView modelAndView = new ModelAndView("finish");
		return modelAndView;
	}

}
