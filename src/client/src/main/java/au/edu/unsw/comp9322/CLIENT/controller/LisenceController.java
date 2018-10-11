package au.edu.unsw.comp9322.CLIENT.controller;

import java.io.IOException;
import java.util.Collection;

import javax.ws.rs.GET;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.comp9322.CLIENT.constant.Constant;
import au.edu.unsw.comp9322.CLIENT.responseModel.NoticeDto;
import au.edu.unsw.comp9322.CLIENT.util.HttpUtil;

@Controller
@RequestMapping("/license")
public class LisenceController {
	private static final Log logger = LogFactory.getLog(LisenceController.class);
	private String REST_URI = Constant.URL + "/license";

	@Autowired
	HttpUtil httpUtil;
	
	/**
	 * Officer ../api/lisence/Renewable Get.
	 * get list of renewable lisence to display in index page
	 * @Workflow 1
	 * @Click http://localhost:8080/ClientServer/api/lisence/renewable
	 * @Call http://localhost:8090/api/license/Renewable
	 * @return
	 * @throws IOException
	 */
	@GET
	@RequestMapping(value = "/renewable")
	public ModelAndView license_renewable(){
		logger.info("renewable");
		
		String URI = REST_URI +"/Renewable";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json"); // setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "officer");

		Collection<NoticeDto> collection = httpUtil.doHttpGet_List(URI,headers,NoticeDto.class);
		//TIDO: do domething with collection
		
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}
}
