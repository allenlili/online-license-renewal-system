package au.edu.unsw.comp9322.REST.service;

import java.util.UUID;

import au.edu.unsw.comp9322.REST.model.Notice;
import au.edu.unsw.comp9322.REST.model.Payment;

public interface PaymentService {

	void generatePaymentFromNotice(Notice notice);

	boolean paymentStatusIsPaidByLicenseId(Long id);

	Payment createPayment(Payment payment);

	Payment getPaymentById(Long id);

	Payment updatePayment(Payment payment);

	boolean paymentStatusIsByNoticeUuid(String status, UUID uuid);

	Payment getPaymentByNoticeId(long noticeId);

}
