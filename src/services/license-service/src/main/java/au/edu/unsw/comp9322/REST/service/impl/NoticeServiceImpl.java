/**
 * service only call it's repository or call other's service
 */
package au.edu.unsw.comp9322.REST.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.REST.constant.Constant;
import au.edu.unsw.comp9322.REST.model.License;
import au.edu.unsw.comp9322.REST.model.Notice;
import au.edu.unsw.comp9322.REST.repository.NoticeRepository;
import au.edu.unsw.comp9322.REST.service.LicenseService;
import au.edu.unsw.comp9322.REST.service.NoticeService;
import au.edu.unsw.comp9322.REST.service.PaymentService;
import au.edu.unsw.comp9322.REST.util.EmailUtil;
import au.edu.unsw.comp9322.REST.util.NoticeUtil;
import au.edu.unsw.comp9322.REST.util.SequenceIdUtil;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	SequenceIdUtil sequenceIdUtil;

	@Autowired
	NoticeRepository noticeRepository;

	@Autowired
	LicenseService licenseService;

	@Autowired
	NoticeService noticeService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	NoticeUtil noticeUtil;

	@Autowired
	EmailUtil emailUtil;

	@Autowired
	Notice notice;

	@Override
	public void initiateRenewalNotice() {
		// get renewal licenses
		List<License> licenseList = new ArrayList<License>();
		licenseList = licenseService.getRenewableLicenses();

		// generate notices from licenses
		List<Notice> NoticeList = new ArrayList<Notice>();
		for (License license : licenseList) {
			Notice tmpNotice = new Notice();
			tmpNotice = noticeService.generateNoticeFromLicense(license);
			NoticeList.add(tmpNotice);
		}

		for (Notice notice : NoticeList) {
			// generate payments from notices
			paymentService.generatePaymentFromNotice(notice);

			String emailAddress = notice.getTmpEmail();
			String emailSubject = "Check your renewal notice";
			String emailBody = "Hi," + licenseService.getLicenseById(notice.getLicenseId()).getDriverName()
					+ "\nyou can renew your license by access notice: "
					+ noticeUtil.generateAccessLinkForDriver(notice);
			emailUtil.sendEmail(emailAddress, emailSubject, emailBody);
		}

	}

	@Override
	public Notice generateNoticeFromLicense(License license) {
		notice = new Notice();
		notice.setId(sequenceIdUtil.getNextSequenceId(Constant.NOTICE_SEQ_KEY));
		notice.setUuid(UUID.randomUUID());
		notice.setStatus("initial");
		notice.setLicenseId(license.getId());
		notice.setTmpAddress(license.getAddress());
		notice.setTmpEmail(license.getEmail());
		notice.setIsExtend("false");
		return noticeRepository.save(notice);
	}

	@Override
	public Notice getNoticeByUuid(UUID uuid) {
		return noticeRepository.findByUuid(uuid);

	}

	@Override
	public boolean noticeStatusIsPaidByLicenseId(Long licenseId) {
		return noticeRepository.findByLicenseId(licenseId).getStatus().equals("paid");
	}

	@Override
	public Notice getNoticeByLicenseId(Long licenseId) {
		return noticeRepository.findByLicenseId(licenseId);
	}

	@Override
	public Notice createNotice(Notice notice) {
		notice.setId(sequenceIdUtil.getNextSequenceId(Constant.NOTICE_SEQ_KEY));
		return noticeRepository.save(notice);
	}

	@Override
	public Notice getNoticeById(Long id) {
		return noticeRepository.findById(id);
	}

	@Override
	public Notice updateNotice(Notice notice) {
		return noticeRepository.save(notice);
	}

	@Override
	public void deleteNoticeByUuid(UUID uuid) {
		notice = noticeRepository.findByUuid(uuid);
		notice.setUuid(null);
		notice.setStatus("archived");
		notice = noticeService.updateNotice(notice);

	}

	@Override
	public boolean noticeStatusIsByNoticeUuid(String status, UUID uuid) {
		return noticeRepository.findByUuid(uuid).getStatus().equals(status);
	}

	@Override
	public List<Notice> getAllNoticeByStatus(String status) {

		return noticeRepository.findByStatusAndOfficerId(status, 0);
	}

	@Override
	public List<Notice> getNoticeOfficerIdByStatus(Long id, String status) {
		return noticeRepository.findByOfficerIdAndStatus(id, status);
	}

}
