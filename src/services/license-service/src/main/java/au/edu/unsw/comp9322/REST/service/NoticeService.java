package au.edu.unsw.comp9322.REST.service;

import java.util.List;
import java.util.UUID;

import au.edu.unsw.comp9322.REST.model.License;
import au.edu.unsw.comp9322.REST.model.Notice;

public interface NoticeService {

	void initiateRenewalNotice();

	Notice generateNoticeFromLicense(License license);

	Notice getNoticeByUuid(UUID uuid);

	boolean noticeStatusIsPaidByLicenseId(Long id);

	Notice getNoticeByLicenseId(Long id);

	Notice createNotice(Notice notice);

	Notice getNoticeById(Long id);

	Notice updateNotice(Notice notice);

	void deleteNoticeByUuid(UUID uuid);

	boolean noticeStatusIsByNoticeUuid(String status, UUID uuid);

	List<Notice> getAllNoticeByStatus(String status);

	List<Notice> getNoticeOfficerIdByStatus(Long id, String status);

}
