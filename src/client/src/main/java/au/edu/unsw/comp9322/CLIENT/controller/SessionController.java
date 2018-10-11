package au.edu.unsw.comp9322.CLIENT.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/session")
@SessionAttributes(value={"attr1","attr2"})
public class SessionController {
    
    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("userModel");
        mav.addObject("attr1", "attr1Value");
        mav.addObject("attr2", "attr2Value");
        return mav;
    }
    
    @RequestMapping("/index2")//getting attribute from model and view
    public ModelAndView index2(@ModelAttribute("attr1")String attr1, @ModelAttribute("attr2")String attr2) {
        ModelAndView mav = new ModelAndView("userModel");
        return mav;
    }
    
    @RequestMapping("/clean")
    public ModelAndView clean(SessionStatus status,HttpSession session) {
    		ModelAndView mav = new ModelAndView("userModel");
    		//not working
    		status.setComplete();
    		//working
    		session.invalidate();
    		//otherwise
//    	session.removeAttribute("hello");
//    	session.removeAttribute("user_name");
//    	session.removeAttribute("password");
    		System.out.println("Cleaning*** Session data ***");   
//    	session.setAttribute("hello", "world");
    		return mav;
    }

    @RequestMapping(value="/check", method=RequestMethod.GET)  
    public String check (Model model, HttpSession session) {// HttpServletRequest request,

        System.out.println("");
        System.out.println("Checking*** Session data ***");
        Enumeration<String> e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String s = e.nextElement();
            System.out.println(s + " == " + session.getAttribute(s));
        }
        return "/userModel";
    }
    
}