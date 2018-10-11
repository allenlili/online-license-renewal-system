package au.edu.unsw.comp9322.CLIENT.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import au.edu.unsw.comp9322.CLIENT.util.HttpUtil;

@Controller
@RequestMapping("/")
public class Welcome {
	@Autowired
	HttpUtil httpUtil;
	
	@RequestMapping(value = "/") 
	public void showMessage(HttpSession session,HttpServletResponse response) {
		try {
			response.sendRedirect("/ClientServer/officer/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
