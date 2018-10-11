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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.comp9322.CLIENT.constant.Constant;
import au.edu.unsw.comp9322.CLIENT.responseModel.NoticeDto;
import au.edu.unsw.comp9322.CLIENT.sendModel.Notice;
import au.edu.unsw.comp9322.CLIENT.service.impl.OfficerNoticeServiceImpl;
import au.edu.unsw.comp9322.CLIENT.util.AuthorizationUtil;
import au.edu.unsw.comp9322.CLIENT.util.HttpUtil;

@Controller
@RequestMapping("/officer/notice")
public class OfficerNoticeController {
//	private static final Log logger = LogFactory.getLog(DriverNoticeController.class);
	Logger logger = Logger.getLogger("OLRS Logger");
	private String REST_URI = Constant.URL + "/notice";

	@Autowired
	HttpUtil httpUtil;
	
	@Autowired
	AuthorizationUtil authorizationUtil;
	
	@Autowired
	OfficerNoticeServiceImpl officerNoticeServiceImpl;
	
	/**
	 * Officer ../api/notice/initiate POST.
	 * Add notice & payment, send email
	 * @Workflow 1
	 * @Click http://localhost:8080/ClientServer/notice/initiate
	 * @Call http://localhost:8090/api/notice/initiate
	 * @return
	 * @throws IOException
	 */
	@POST
	@RequestMapping(value = "/initiate")
	public void notice_initiate( HttpServletResponse response, HttpSession session) throws IOException {
		logger.info("Initiate");
		
		String URI = REST_URI +"/initiate";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json"); // setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "officer");
			
		//this is useless
		NoticeDto noticeDto = (NoticeDto) httpUtil.doHttpPostEmpty(URI,headers,NoticeDto.class);
		
		session.setAttribute("initiate", "true");
		try {
			response.sendRedirect("/ClientServer/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * For submit notice object later???
	 * @Workflow ??
	 * @Click http://localhost:8080/ClientServer/notice/submit
	 * @Call http://localhost:8090/api/notice
	 * @return NoticeDto
	 * @throws IOException
	 */
	@RequestMapping(value = "/submit", consumes = MediaType.APPLICATION_JSON)
	public ModelAndView notice_post() throws IOException {
		logger.info("Notice Post");

		// create request body
		//return a notice, will receive first
		Notice notice = Notice.CreateTestNotice();
				
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json"); // setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "officer");
				
		NoticeDto noticeDto = (NoticeDto) httpUtil.doHttpPost(REST_URI,headers,notice,NoticeDto.class);
		
		//do something with noticeDto
		
		ModelAndView modelAndView = new ModelAndView("helloworld");
		return modelAndView;
	}
	
	@RequestMapping(value = "/manual/assign_manual_validate")
	public void assign_manual_validate(@RequestParam("uuid") UUID uuid, HttpServletResponse response,HttpSession session){	
		logger.info("Assign for manual validate");
		//update db
		//update db->set thie task  to himself
		NoticeDto checkRequest = officerNoticeServiceImpl.assignManualValidate(session, uuid);
		Boolean checkUpdate = officerNoticeServiceImpl.insertNoticeTable(session, checkRequest);
		
		try {
			response.sendRedirect("/ClientServer/officer/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/manual/assign_manual_extend")
	public void assign_manual_extend(@RequestParam("uuid") UUID uuid,HttpServletResponse response,HttpSession session){	
		logger.info("Assign for manual extend");
		System.out.println("assign_manual_extend");
		System.out.println(session.getAttribute("Identity").toString());
//		System.out.println(uuid);
		
		//update db->set thie task  to himself
		NoticeDto checkRequest = officerNoticeServiceImpl.assignManualExtend(session, uuid);
		Boolean checkUpdate = officerNoticeServiceImpl.insertNoticeTable(session, checkRequest);		
		
		try {
			response.sendRedirect("/ClientServer/officer/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/manual/close_manual_validate")
	public void close_manual_validate(@RequestParam("uuid") UUID uuid, HttpServletResponse response,HttpSession session){	
		logger.info("Approve for manual validate");
		session.setAttribute("enter_amount_manual_validate", "true");
		session.setAttribute("uuid_for_amount", uuid);	
		try {
			response.sendRedirect("/ClientServer/officer/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/manual/close_manual_extend")
	public void close_manual_extend(@RequestParam("uuid") UUID uuid, HttpServletResponse response,HttpSession session){	
		logger.info("Approve for manual extend");
		session.setAttribute("enter_amount_manual_extend", "true");
		session.setAttribute("uuid_for_amount", uuid);	
		
		try {
			response.sendRedirect("/ClientServer/officer/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//after enter payment for both action
	@RequestMapping(value = "/manual/update_amount")
	public void update_payment_amount(@RequestParam("amount") int amount , HttpServletResponse response,HttpSession session){	
		logger.info("Update payment amount");
		
		UUID uuid = (UUID) session.getAttribute("uuid_for_amount");
		session.removeAttribute("enter_amount_manual_validate");
		session.removeAttribute("enter_amount_manual_extend");
		System.out.println("set uuid "+uuid+" to amount "+ amount);
		//send to service
		officerNoticeServiceImpl.updatePayment(session, uuid, amount);
		//
		try {
			response.sendRedirect("/ClientServer/officer/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	//before enter rejection reson
	@RequestMapping(value = "/manual/reject_manual_validate")
	public void reject_manual_validate(@RequestParam("uuid") UUID uuid, HttpServletResponse response,HttpSession session){	
		logger.info("Reject manual validate");
		session.setAttribute("enter_reject_validate_reason", "true");
		session.setAttribute("uuid_for_reject", uuid);
		try {
			response.sendRedirect("/ClientServer/officer/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//before enter rejection reson
	@RequestMapping(value = "/manual/reject_manual_extend")
	public void reject_manual_extend(@RequestParam("uuid") UUID uuid,HttpServletResponse response,HttpSession session){	
		logger.info("Reject for manual extend");
		session.setAttribute("enter_reject_extend_reason", "true");
		session.setAttribute("uuid_for_reject", uuid);
		try {
			response.sendRedirect("/ClientServer/officer/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//after enter rejection reason for both action
	@RequestMapping(value = "/manual/submit_reason_reject_manual")
	public void submit_reason_reject_manual(@RequestParam("rejection_reason") String rejection_reason, HttpServletResponse response,HttpSession session){	
		logger.info("Update reject reason manual");
		
		UUID uuid = (UUID) session.getAttribute("uuid_for_reject");
		session.removeAttribute("enter_reject_extend_reason");
		session.removeAttribute("enter_reject_validate_reason");
		
		//send to service
		officerNoticeServiceImpl.sendRejection(session, uuid, rejection_reason);
		//
		try {
			response.sendRedirect("/ClientServer/officer/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @Workflow ??
	 * @Click http://localhost:8080/ClientServer/notice/{notice_id}
	 * @Call http://localhost:8090/api/notice/{notice_id}
	 * @param notice_id
	 * @return NoticeDto of one driver's intormation
	 * @throws IOException
	 */
	@RequestMapping(value = "/{notice_id}")
	public ModelAndView notice_id_get(@PathVariable int notice_id) throws IOException {	
		logger.info("Get notice by id");
		System.out.println("notice_id_get");
		String URI = REST_URI +"/"+notice_id;
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "officer");
		
		NoticeDto noticeDto = (NoticeDto) httpUtil.doHttpGet(URI,headers,NoticeDto.class);
		
		//TODO: Do something with noticeDto
		
		ModelAndView modelAndView = new ModelAndView("helloworld");
		return modelAndView;
	}
	
	
}
