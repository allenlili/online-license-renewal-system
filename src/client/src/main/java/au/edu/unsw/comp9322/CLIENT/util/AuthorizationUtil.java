package au.edu.unsw.comp9322.CLIENT.util;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public class AuthorizationUtil {

	public void setAuthorization (String authorization,Object object, HttpSession session) {

		session.setAttribute("Authorization", authorization);
		session.setAttribute("Identity", object);

        Enumeration<String> e = session.getAttributeNames();

    }
	
	public boolean isDriver(HttpSession session) {
		if (session.getAttribute("Authorization")!=null) {
			if(session.getAttribute("Authorization")=="driver")
				return true;
		}
		return false;
	}
	public boolean isOfficer(HttpSession session) {
		if (session.getAttribute("Authorization")!=null) {
			if(session.getAttribute("Authorization")=="officer")
				return true;
		}
		return false;
	}
}
