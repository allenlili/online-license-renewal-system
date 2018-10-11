package au.edu.unsw.comp9322.CLIENT.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.CLIENT.bean.Status;
import au.edu.unsw.comp9322.CLIENT.responseModel.NoticeDto;
import au.edu.unsw.comp9322.CLIENT.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {
	//ArrayList<String> progress = new ArrayList<>(Arrays.asList("initial", "valid","extension","payment","complete"));
	
	@Override
	public Status updateStatus(HttpSession session) {
		String status = ((NoticeDto) session.getAttribute("driverNoticeInfoDto")).getStatus();
		Status statusObj =(Status) session.getAttribute("status");
		if(statusObj == null) {
			statusObj = new Status("","","","","");
		}
		//TODO : a lot of manipulation
		if(status.equals("initial")) {
			statusObj.setInitial("active");
			statusObj.setValid("");
			statusObj.setExtension("");
			statusObj.setPayment("");
			statusObj.setComplete("");
		}
		else if(status.equals("valid")||status.equals("invalid")||status.equals("manual_validate")) {
			statusObj.setInitial("visited");
			statusObj.setValid("active");
			statusObj.setExtension("");
			statusObj.setPayment("");
			statusObj.setComplete("");
		}else if(status.equals("manual_extend")){
			statusObj.setInitial("visited");
			statusObj.setValid("visited");	
			statusObj.setExtension("active");
			statusObj.setPayment("");
			statusObj.setComplete("");
		}else if(status.equals("unpaid")) {
			statusObj.setInitial("visited");
			statusObj.setValid("visited");	
			statusObj.setExtension("visited");
			statusObj.setPayment("active");
			statusObj.setComplete("");
		}else if(status.equals("paid")||status.equals("rejected")) {
			statusObj.setInitial("visited");
			statusObj.setValid("visited");	
			statusObj.setExtension("visited");
			statusObj.setPayment("visited");
			statusObj.setComplete("active");
		}
		System.out.println(status+": "+statusObj.toString());
		session.setAttribute("status", statusObj);
		return statusObj;
	}

}
