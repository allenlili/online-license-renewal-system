package au.edu.unsw.comp9322.CLIENT.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.CLIENT.bean.AddressAndEmailDto;
import au.edu.unsw.comp9322.CLIENT.constant.Constant;
import au.edu.unsw.comp9322.CLIENT.responseModel.NoticeDto;
import au.edu.unsw.comp9322.CLIENT.responseModel.PaymentDto;
import au.edu.unsw.comp9322.CLIENT.sendModel.Notice;
import au.edu.unsw.comp9322.CLIENT.service.DriverNoticeService;
import au.edu.unsw.comp9322.CLIENT.util.HttpUtil;
import au.edu.unsw.comp9322.CLIENT.util.ModelUtil;

@Service
public class DriverNoticeServiceImpl implements DriverNoticeService{

	@Autowired
	ModelUtil modelUtil;
	
	@Autowired
	HttpUtil httpUtil;
	
	private String REST_URI = Constant.URL + "/notice";
	
	@Override
	public Boolean driverUpdateNotice(HttpSession session) {
		NoticeDto noticeDto = (NoticeDto) session.getAttribute("driverNoticeInfoDto");
		System.out.println(noticeDto.toString());
		//send to service database
		//change to Notice object & UPDATE INPPUT
		Notice notice = modelUtil.NoticeDtoToNotice(noticeDto);
		
		//actual put
		String URI = REST_URI+"/uuid/"+noticeDto.getUuid();
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "driver");
		headers.set("Content-Type", "application/json"); 
		
		NoticeDto noticeDtoResponse = (NoticeDto) httpUtil.doHttpPut(URI,headers,notice,NoticeDto.class);
		System.out.println("sending:n"+notice.toString());
		System.out.println("Response:\n " + noticeDtoResponse.toString());
		return true;
	}

	@Override
	public NoticeDto updateNoticeDtoAddressEmail(HttpSession session, NoticeDto noticeDto,  AddressAndEmailDto addressAndEmailDto) {
		noticeDto = modelUtil.updateAddressEmail(noticeDto,addressAndEmailDto);
		return noticeDto;
	}

	@Override
	public boolean putPaymentDtoIntoSession(HttpSession session) {
		NoticeDto noticeDto = (NoticeDto) session.getAttribute("driverNoticeInfoDto");
		if(noticeDto.getStatus().equals("unpaid")) {
			// update service database: http
			String URI = Constant.URL + "/payment/uuid/" + noticeDto.getUuid();
			// set headers
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "driver");
			PaymentDto paymentDto= (PaymentDto) httpUtil.doHttpGet(URI, headers, PaymentDto.class);
			System.out.println(noticeDto.toString());
			session.setAttribute("driverPaymentInfoDto", paymentDto);
		}
		return false;
	}



}
