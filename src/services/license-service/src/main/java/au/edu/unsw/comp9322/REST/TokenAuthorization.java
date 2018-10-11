package au.edu.unsw.comp9322.REST;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.comp9322.REST.constant.Constant;

public class TokenAuthorization implements HandlerInterceptor {

	// This method is called before the controller
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		List<String> driver_urls = new ArrayList<String>();
		List<String> officer_urls = new ArrayList<String>();

		// TODO add url for driver can access
		driver_urls.add("/api/notice/uuid/[^\\/]*$");
		driver_urls.add("/api/notice/uuid/[^\\/]*/validate");
		driver_urls.add("/api/notice/uuid/[^\\/]*/manual_validate");
		driver_urls.add("/api/notice/uuid/[^\\/]*/manual_extend");
		driver_urls.add("/api/notice/uuid/[^\\/]*/not_extend");
		driver_urls.add("/api/notice/uuid/[^\\/]*/archive");
		driver_urls.add("/api/payment/uuid/[^\\/]*$");
		driver_urls.add("/api/payment/\\d+/paid$");

		officer_urls = driver_urls;
		// TODO add url for officer can access
		officer_urls.add("/api/notice/initiate");
		officer_urls.add("/api/license/\\d+");
		officer_urls.add("/api/payment");
		officer_urls.add("/api/payment/[^\\/]*$");
		officer_urls.add("/api/notice");
		officer_urls.add("/api/notice/[^\\/]*$");
		officer_urls.add("/api/notice/status/manual_validate");
		officer_urls.add("/api/notice/status/manual_extend");
		officer_urls.add("/api/notice/uuid/[^\\/]*/officer/\\d+$");
		officer_urls.add("/api/notice/officer/\\d+/claimed");
		officer_urls.add("/api/notice/uuid/[^\\/]*/officer/\\d+/approve/amount/\\d+$");
		officer_urls.add("/api/notice/uuid/[^\\/]*/officer/\\d+/reject$");

		officer_urls.add("/api/license/Renewable");

		String authorization_token = null;
		authorization_token = request.getHeader("Authorization");
		System.out.println("authorization_token: " + authorization_token);
		String requestPath = request.getRequestURI();
		System.out.println("requestPath: " + requestPath);

		System.out.println(request.getRequestURL());

		if (authorization_token == null) {
			response.setStatus(401);
			response.getWriter().append("authorization required");
			return false;

		} else if (authorization_token.equals(Constant.DRIVER_AUTHORIZATION_TOKEN)) {

			for (String driver_url : driver_urls) {
				if (requestPath.matches(driver_url)) {
					System.out.println(driver_url);
					return true;
				}
			}

			response.setStatus(401);
			response.getWriter().append("failure of authorization of driver");
			return false;

		} else if (authorization_token.equals(Constant.OFFICER_AUTHORIZATION_TOKEN)) {

			for (String officer_url : officer_urls) {
				if (requestPath.matches(officer_url)) {
					System.out.println(officer_url);
					return true;
				}
			}

			response.setStatus(401);
			response.getWriter().append("failure of authorization of officer");
			return false;

		} else {
			response.setStatus(401);
			response.getWriter().append("authorization required");
			return false;
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}