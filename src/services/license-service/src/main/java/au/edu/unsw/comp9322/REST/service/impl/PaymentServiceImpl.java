package au.edu.unsw.comp9322.REST.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.REST.constant.Constant;
import au.edu.unsw.comp9322.REST.model.License;
import au.edu.unsw.comp9322.REST.model.Notice;
import au.edu.unsw.comp9322.REST.model.Payment;
import au.edu.unsw.comp9322.REST.repository.PaymentRepository;
import au.edu.unsw.comp9322.REST.service.LicenseService;
import au.edu.unsw.comp9322.REST.service.NoticeService;
import au.edu.unsw.comp9322.REST.service.PaymentService;
import au.edu.unsw.comp9322.REST.util.SequenceIdUtil;

@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	Payment payment;

	@Autowired
	SequenceIdUtil sequenceIdUtil;

	@Autowired
	NoticeService noticeService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	License license;

	@Autowired
	LicenseService licenseService;

	@Autowired
	Notice notice;

	@Override
	public void generatePaymentFromNotice(Notice notice) {
		payment = new Payment();

		payment.setId(sequenceIdUtil.getNextSequenceId(Constant.PAYMENT_SEQ_KEY));
		payment.setStatus("initial");
		payment.setAmount("100"); // default amount
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		payment.setInitialDate(dateFormat.format(new Date()));
		payment.setNoticeId(notice.getId());

		paymentRepository.save(payment);

	}

	@Override
	public boolean paymentStatusIsPaidByLicenseId(Long id) {
		license = licenseService.getLicenseById(id);
		if (license == null) {
			return false;
		}
		notice = noticeService.getNoticeByLicenseId(id);
		if (notice == null) {
			return false;
		}

		return paymentRepository.findByNoticeId(notice.getId()).getStatus().equals("paid");
	}

	@Override
	public Payment createPayment(Payment payment) {
		payment.setId(sequenceIdUtil.getNextSequenceId(Constant.PAYMENT_SEQ_KEY));
		return paymentRepository.save(payment);
	}

	@Override
	public Payment getPaymentById(Long id) {
		return paymentRepository.findById(id);
	}

	@Override
	public Payment updatePayment(Payment payment) {
		return paymentRepository.save(payment);
	}

	@Override
	public boolean paymentStatusIsByNoticeUuid(String status, UUID uuid) {
		notice = noticeService.getNoticeByUuid(uuid);
		if (notice == null) {
			return false;
		}
		return paymentRepository.findByNoticeId(notice.getId()).getStatus().equals(status);
	}

	@Override
	public Payment getPaymentByNoticeId(long noticeId) {
		return paymentRepository.findByNoticeId(noticeId);
	}

}
