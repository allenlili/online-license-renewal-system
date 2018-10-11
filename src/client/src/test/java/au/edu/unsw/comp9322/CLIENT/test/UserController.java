package au.edu.unsw.comp9322.CLIENT.test;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller
//@RequestMapping("/")
//@SessionAttributes("userId")
public class UserController {
	
	private static final Log logger = LogFactory.getLog(UserController.class);
	
	@RequestMapping(value="/user/login", method=RequestMethod.GET)
    public String login (
            int id, Model model, HttpServletRequest request, HttpSession session) {
        
        model.addAttribute("userId", id);
        
        System.out.println("");
        System.out.println("");
        System.out.println("inside login");
        
        System.out.println("");
        System.out.println("--- Model data ---");
        Map modelMap = model.asMap();
        for (Object modelKey : modelMap.keySet()) {
            Object modelValue = modelMap.get(modelKey);
            System.out.println(modelKey + " -- " + modelValue);
        }

        System.out.println("");
        System.out.println("*** Session data ***");
        Enumeration<String> e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String s = e.nextElement();
            System.out.println(s + " == " + session.getAttribute(s));
        }

        return "/test";
    }
    
//    @RequestMapping(value="/check", method=RequestMethod.GET)
//    public String check (Model model, HttpSession session) {// HttpServletRequest request,
//
//        System.out.println("");
//        System.out.println("*** Session data ***");
//        Enumeration<String> e = session.getAttributeNames();
//        while (e.hasMoreElements()) {
//            String s = e.nextElement();
//            System.out.println(s + " == " + session.getAttribute(s));
//        }
//
//        return "/test";
//    }
	
	

	//this function will be called before "login" function, in order to set attributes returned from url
	//ie: http://localhost:8080/Client/login?user_name=YIJUN&passworld=123456
/*	@ModelAttribute
	public void userModel( String user_name, String password, Model mpdel) {
		logger.info("userModel");
		User user = new User(user_name, password);
		mpdel.addAttribute(user);
	}
	
	@RequestMapping(value="/login")
	public String login(Model model) {
		logger.info("login");
		User user = (User)model.asMap().get("user");
		System.out.println(user.getUser_name() + " " + user.getPassword());
		return "userModel";
	}
*/
/*	@ModelAttribute
	public void userModel(String user_name, String password, ModelMap modelMap) {
		logger.info("userModel");
		User user = new User(user_name, password);
		modelMap.addAttribute(user);
	}
	
	@RequestMapping(value="/login")
	public String login(ModelMap modelMap) {
		logger.info("login");
		User user = (User)modelMap.get("user");
		System.out.println(user.getUser_name() + " " + user.getPassword());
		return "userModel";
	}
*/
//	@ModelAttribute
//	public void userModel(String user_name, String password, ModelAndView modelAndView) {
//		logger.info("userModel");
//		Officer officer = new Officer(user_name, password);
//		modelAndView.addObject(officer);
//	}
//	
//	
//	@RequestMapping(value="/login")
//	public ModelAndView login(ModelAndView modelAndView) {
//		logger.info("login");
//		Officer officer = (Officer)modelAndView.getModel().get("officer");
//		System.out.println(officer.toString());
//		modelAndView.setViewName("userModel");
//		return modelAndView;
//	}

}
