package au.edu.unsw.comp9322.CLIENT.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.comp9322.CLIENT.bean.MyOfficer;
import au.edu.unsw.comp9322.CLIENT.dao.OfficerDao;
import au.edu.unsw.comp9322.CLIENT.service.impl.OfficerNoticeServiceImpl;
import au.edu.unsw.comp9322.CLIENT.util.AuthorizationUtil;
import au.edu.unsw.comp9322.CLIENT.util.HttpUtil;

@Controller
@RequestMapping("/officer")
//@SessionAttributes(value= {"officer","Authorization"})
public class OfficerController {
	
	private Logger logger = Logger.getLogger("OLRS Logger");
	
	@Autowired
	OfficerDao officerDao;
	
	@Autowired
	AuthorizationUtil authorizationUtil;
	

	@Autowired
	HttpUtil httpUtil;
	
	@Autowired
	OfficerNoticeServiceImpl officerNoticeServiceImpl;
	
	@RequestMapping(value = "/") 
	public ModelAndView showMessage(HttpSession session) {
		//only officer have access to this page
		logger.info("Officer Work Page");
		ModelAndView modelAndView = new ModelAndView("index");
		
		//set renewable license into session
		officerNoticeServiceImpl.putRenewableLicenseInSession(session);
		
		//set new manual_validate into session
		officerNoticeServiceImpl.putManualValidateRequestInSession(session);
		
		//set new manual_extend into session
		officerNoticeServiceImpl.putManualExtendRequestInSession(session);
		
		//set open manual_validate into session
		officerNoticeServiceImpl.putPendingManualValidateInSession(session);
		
		//set open manual_extend into session
		officerNoticeServiceImpl.putPendingManualExtendInSession(session);
		
		return modelAndView;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/login", method= {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView login(//@RequestParam(value="user_name",required=false,defaultValue="selina") 
			String user_name, //@RequestParam(value="password",required=false,defaultValue="123456")
			String password,
			HttpServletResponse response, HttpSession session) {
		logger.info("Login");
		ModelAndView modelAndView = new ModelAndView();
		
		if(user_name==null || password==null) 
			modelAndView.setViewName("login");//login page
		else {
			//TODO: Confirm officer identity with id&password
			MyOfficer officer = officerDao.getOfficerByNamePassword(user_name, password);
			if(officer== null) {
				System.out.println("Login error");
				session.setAttribute("login_errormsg", "wrong");
				modelAndView.setViewName("login");
			}else {
//				System.out.println("Login with: " + officer.toString());
				if(session.getAttribute("login_errormsg")!=null) {
					session.removeAttribute("login_errormsg");
				}
				authorizationUtil.setAuthorization("officer",officer,session);
				modelAndView.setViewName("index");
				try {
					response.sendRedirect("/ClientServer/officer/");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return modelAndView;
	}

	@RequestMapping("/logout")
    public ModelAndView logout(SessionStatus status,HttpSession session) {
		logger.info("Logout");
    		ModelAndView mav = new ModelAndView("login");
    		//not working
    		status.setComplete();
    		//working
    		session.invalidate();
    		//otherwise
//    	session.removeAttribute("hello");
//    	session.removeAttribute("user_name");
//    	session.removeAttribute("password");
    		System.out.println("Logging out*** Session data ***");   
//    	session.setAttribute("hello", "world");
    		return mav;
    }
}
