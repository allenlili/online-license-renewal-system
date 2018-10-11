package au.edu.unsw.comp9322.CLIENT.service;

import javax.servlet.http.HttpSession;

import au.edu.unsw.comp9322.CLIENT.bean.AddressAndEmailDto;
import au.edu.unsw.comp9322.CLIENT.responseModel.NoticeDto;

public interface DriverNoticeService {
	public NoticeDto updateNoticeDtoAddressEmail(HttpSession session, NoticeDto noticeDto,  AddressAndEmailDto addressAndEmailDto);
	
	public Boolean driverUpdateNotice(HttpSession session);
	
	public boolean putPaymentDtoIntoSession(HttpSession session);
}
