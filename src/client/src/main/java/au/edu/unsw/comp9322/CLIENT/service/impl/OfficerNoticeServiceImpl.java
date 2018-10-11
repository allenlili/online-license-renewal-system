package au.edu.unsw.comp9322.CLIENT.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.CLIENT.bean.MyNotice;
import au.edu.unsw.comp9322.CLIENT.bean.MyOfficer;
import au.edu.unsw.comp9322.CLIENT.constant.Constant;
import au.edu.unsw.comp9322.CLIENT.dao.NoticeDao;
import au.edu.unsw.comp9322.CLIENT.responseModel.NoticeDto;
import au.edu.unsw.comp9322.CLIENT.service.OfficerNoticeService;
import au.edu.unsw.comp9322.CLIENT.util.HttpUtil;
import au.edu.unsw.comp9322.CLIENT.util.ModelUtil;
@Service
public class OfficerNoticeServiceImpl implements OfficerNoticeService {

	@Autowired
	ModelUtil modelUtil;
	
	@Autowired
	HttpUtil httpUtil;
	
	@Autowired
	NoticeDao noticeDao;
	
//	private String REST_URI = Constant.URL + "/notice";
	
	@Override
	public boolean putRenewableLicenseInSession(HttpSession session) {
		String URI = Constant.URL + "/license/Renewable";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json"); // setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "officer");
		Collection<NoticeDto> collection = httpUtil.doHttpGet_List(URI,headers,NoticeDto.class);
		
		//TODO: do domething with collection
		
		session.setAttribute("renewable_license",collection);
		return true;
	}
	
	@Override
	public boolean putManualValidateRequestInSession(HttpSession session) {
		String URI = Constant.URL + "/notice/status/manual_validate";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "officer");
		Collection<NoticeDto> collection = httpUtil.doHttpGet_List(URI,headers,NoticeDto.class);
		
		//TODO: do domething with collection
		
		session.setAttribute("request_manual_validate",collection);
		return true;
	}
	
	@Override
	public boolean putManualExtendRequestInSession(HttpSession session) {
		String URI = Constant.URL + "/notice/status/manual_extend";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "officer");
		Collection<NoticeDto> collection = httpUtil.doHttpGet_List(URI,headers,NoticeDto.class);
		//TODO: do domething with collection	
		session.setAttribute("request_manual_extend",collection);
		return true;
	}
	@Override
	public NoticeDto assignManualValidate(HttpSession session, UUID uuid) {
		MyOfficer officer = (MyOfficer)session.getAttribute("Identity");
		String URI = Constant.URL + "/notice/uuid/"+ uuid +"/officer/"+officer.getId();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "officer");
		return (NoticeDto) httpUtil.doHttpPutEmpty(URI, headers, NoticeDto.class);
	}
	
	@Override
	public NoticeDto assignManualExtend(HttpSession session, UUID uuid) {
		MyOfficer officer = (MyOfficer)session.getAttribute("Identity");
		String URI = Constant.URL + "/notice/uuid/"+ uuid +"/officer/"+officer.getId();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "officer");
		return (NoticeDto) httpUtil.doHttpPutEmpty(URI, headers, NoticeDto.class);
	}

	public Boolean insertNoticeTable(HttpSession session, NoticeDto checkRequest) {
		MyOfficer officer = (MyOfficer)session.getAttribute("Identity");
		return noticeDao.insertNotice(checkRequest.getId(), checkRequest.getUuid(), officer.getId(), checkRequest.getStatus());
		
	}

	public boolean putPendingManualValidateInSession(HttpSession session) {
		MyOfficer officer = (MyOfficer)session.getAttribute("Identity");
		List<MyNotice> collection = noticeDao.getPendingManualValidate(officer.getId());
		List<NoticeDto>noticeDtoList =null;
		if(collection != null) {
//			System.out.println("COLLECTION VALUE:"+collection.toString());
			noticeDtoList = new ArrayList<NoticeDto>();
			for(MyNotice notice: collection) {
//				System.out.println(notice.getNotice_uuid().toString());
				//for each notice, get thenoticeDto according to notice_id
				NoticeDto noticeDto = getNoticeDtoById(notice.getNotice_id());
				noticeDtoList.add(noticeDto);
			}
			session.setAttribute("pending_manual_validate",noticeDtoList);
		}else {
			if(session.getAttribute("pending_manual_validate") != null) {
				session.removeAttribute("pending_manual_validate");
			}
		}
		return true;
	}

	public boolean putPendingManualExtendInSession(HttpSession session) {
		MyOfficer officer = (MyOfficer)session.getAttribute("Identity");
		List<MyNotice> collection = noticeDao.getPendingManualExtend(officer.getId());
		List<NoticeDto>noticeDtoList = null;
		if(collection != null) {
			noticeDtoList = new ArrayList<NoticeDto>();
			for(MyNotice notice: collection) {
				NoticeDto noticeDto = getNoticeDtoById(notice.getNotice_id());
				noticeDtoList.add(noticeDto);
			}
			session.setAttribute("pending_manual_extend",noticeDtoList);
		}else {
			if(session.getAttribute("pending_manual_extend") != null) {
				session.removeAttribute("pending_manual_extend");
			}
		}	
		return true;
	}

	private NoticeDto getNoticeDtoById(long notice_id) {
		String URI = Constant.URL + "/notice/"+notice_id;
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "officer");
		
		NoticeDto noticeDto = (NoticeDto) httpUtil.doHttpGet(URI,headers,NoticeDto.class);
		return noticeDto;
		
	}

	@Override
	public NoticeDto sendRejection(HttpSession session, UUID uuid,  String rejection_reason) {
		MyOfficer officer = (MyOfficer)session.getAttribute("Identity");
		String URI = Constant.URL + "/notice/uuid/"+ uuid +"/officer/"+officer.getId()+"/reject";	
		//update service
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json"); // setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "officer");
		NoticeDto noticeDtoSend = new NoticeDto();
		noticeDtoSend.setRejectReason(rejection_reason);
		NoticeDto noticeDto = (NoticeDto) httpUtil.doHttpPut(URI,headers,noticeDtoSend,NoticeDto.class);
		
		//get notice id
		NoticeDto noticeDto_forid = getNoticeDtoByUUID(uuid);
		
		//update client db
		noticeDao.sendStatus(noticeDto_forid.getId(),"rejected");
		return noticeDto;
	}

	public void updatePayment(HttpSession session, UUID uuid, int amount) {
		//../api/notice/uuid/{notice _uuid}/officer/{officer_id}/approve/amount/{amount}
		MyOfficer officer = (MyOfficer)session.getAttribute("Identity");
		String URI = Constant.URL + "/notice/uuid/"+ uuid +"/officer/"+officer.getId()+"/approve/amount/"+amount;	
		//update service
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "officer");
		NoticeDto noticeDtoSend = new NoticeDto();
		NoticeDto noticeDto = (NoticeDto) httpUtil.doHttpPutEmpty(URI,headers,NoticeDto.class);
		
		//get notice id
		NoticeDto noticeDto_forid = getNoticeDtoByUUID(uuid);
		
		//update client db
		noticeDao.sendStatus(noticeDto_forid.getId(),"unpaid");
	}
	
	@Override
	public NoticeDto getNoticeDtoByUUID(UUID uuid) {
		//get notice id
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "driver");
		String URI = Constant.URL + "/notice/uuid/" + uuid;
		// get all info about this driver
		return  (NoticeDto) httpUtil.doHttpGet(URI, headers, NoticeDto.class);
	}
}
