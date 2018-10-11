package au.edu.unsw.comp9322.CLIENT.service;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import au.edu.unsw.comp9322.CLIENT.responseModel.NoticeDto;

public interface OfficerNoticeService {
	public boolean putRenewableLicenseInSession(HttpSession session);
	
	public boolean putManualValidateRequestInSession(HttpSession session);
	
	public boolean putManualExtendRequestInSession(HttpSession session);
	
	public NoticeDto assignManualValidate(HttpSession session, UUID uuid);
	
	public NoticeDto assignManualExtend(HttpSession session, UUID uuid);
	
	public NoticeDto sendRejection(HttpSession session, UUID uuid,  String rejection_reasons);

	public NoticeDto getNoticeDtoByUUID(UUID uuid) ;
}
