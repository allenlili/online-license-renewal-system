package au.edu.unsw.comp9322.CLIENT.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.comp9322.CLIENT.responseModel.NoticeDto;
import au.edu.unsw.comp9322.CLIENT.util.AuthorizationUtil;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	AuthorizationUtil authorizationUtil;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
//		System.out.println("afterCompletion()===================");

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2, ModelAndView modelAndView)
			throws Exception {
//		System.out.println("postHandle()===================");


	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		System.out.println("\npreHandle()===================");
		String url = request.getRequestURI();  
         
		HttpSession session = request.getSession();   
        
        if(url.indexOf("session")>=0){
        		return true;
        }
        if(url.indexOf("driver")>=0){
        		if(authorizationUtil.isDriver(session))
        			return true;
        		else if(url.indexOf("driver/notice/uuid")>=0)
        			return true;
        		response.sendRedirect("/ClientServer/driver/notice/uuid/"+((NoticeDto)session.getAttribute("driverNoticeInfoDto")).getUuid()); 
        		return false;
	    }
        else if(url.indexOf("login")>=0){  
        		if(authorizationUtil.isOfficer(session)) {
//        			System.out.println("officer already login!!! redirect to /ClientServer");
        			response.sendRedirect("/ClientServer");
        			return false;
        		}
      		return true;
      	}
        else if(authorizationUtil.isOfficer(session)) {
	        		return true;
        }
    		response.sendRedirect("/ClientServer/officer/login");
    		return false;  
	}
//	
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		System.out.println("\npreHandle()===================");
//		String url = request.getRequestURI();  
//         
//		HttpSession session = request.getSession();  
//        String authorization = (String)session.getAttribute("Authorization");  
//        
//        if(url.indexOf("session")>=0){
//        		return true;
//        }
//        if(url.indexOf("driver")>=0){
//        		System.out.println("here");
//	    		return true;
//	    }	
//        if(url.indexOf("login")>=0){  
//        		if(authorization =="officer"){ 
//        			System.out.println("officer already login!!! redirect to /ClientServer/");
//        			response.sendRedirect("/ClientServer");
//        			return false;
//        		}
//            return true;  
//        } 
//        if(authorization =="officer"){  
//            return true;  
//        } else if(authorization =="driver"){  
//             
//        }  
//        //TODO: or jump to a controller where some source can be viewed
//		response.sendRedirect("/ClientServer/officer/login"); 
////		request.getRequestDispatcher("/ClientServer/officer/login").forward(request, response);
//        return false;  
//	}

}
