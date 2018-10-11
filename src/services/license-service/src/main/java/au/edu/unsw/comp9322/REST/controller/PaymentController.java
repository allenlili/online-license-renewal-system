package au.edu.unsw.comp9322.REST.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.edu.unsw.comp9322.REST.dto.LicenseDto;
import au.edu.unsw.comp9322.REST.dto.PaymentDto;
import au.edu.unsw.comp9322.REST.model.License;
import au.edu.unsw.comp9322.REST.model.Notice;
import au.edu.unsw.comp9322.REST.model.Payment;
import au.edu.unsw.comp9322.REST.service.LicenseService;
import au.edu.unsw.comp9322.REST.service.NoticeService;
import au.edu.unsw.comp9322.REST.service.PaymentService;
import au.edu.unsw.comp9322.REST.util.LicenseUtil;
import au.edu.unsw.comp9322.REST.util.PaymentUtil;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	@Autowired
	Payment payment;

	@Autowired
	PaymentService paymentService;

	@Autowired
	PaymentUtil paymentUtil;

	@Autowired
	Notice notice;

	@Autowired
	NoticeService noticeService;

	@Autowired
	LicenseUtil licenseUitl;

	@Autowired
	License license;

	@Autowired
	LicenseService licenseService;

	private final java.util.logging.Logger logger = java.util.logging.Logger
			.getLogger(NoticeController.class.getName());

	/**
	 * Required: create payment
	 *
	 * ../api/payment POST
	 *
	 * accessible: officer
	 *
	 * @param payment
	 * @return PaymentDto
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PaymentDto createPayment(@RequestBody Payment payment) {

		logger.info("../api/payment POST Payment:" + payment.toString());

		try {
			payment = paymentService.createPayment(payment);
			return paymentUtil.toDto(payment, "succeed to create payment", null);
		} catch (Exception e) {
			e.printStackTrace();
			return paymentUtil.toDto(null, e.toString(), "513");
		}
	}

	/**
	 * Required: update payment by id
	 *
	 * ../api/payment/{id} PUT
	 *
	 * accessible: officer
	 *
	 * @param id
	 * @param payment
	 * @return PaymentDto
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PaymentDto updatePayment(@RequestBody Payment newPayment, @PathVariable("id") Long id) {

		logger.info("../api/payment/{id} PUT Payment:" + newPayment.toString() + " id:" + id);

		try {
			// check id if valid
			payment = paymentService.getPaymentById(id);
			if (payment == null) {
				return paymentUtil.toDto(null, "payment id:" + id + " invalid", "511");
			}

			// update payment
			newPayment.setId(payment.getId());
			payment = paymentService.updatePayment(newPayment);
			return paymentUtil.toDto(payment, "success to update payment", null);
		} catch (Exception e) {
			e.printStackTrace();
			return paymentUtil.toDto(null, e.toString(), "513");
		}

	}

	/**
	 * Required: get payment by id
	 *
	 * ../api/payment/{id} GET
	 *
	 * accessible: officer
	 *
	 * @param id
	 * @return PaymentDto
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PaymentDto getPaymentById(@PathVariable("id") Long id) {

		logger.info("../api/payment/{id} GET id:" + id);

		try {
			payment = paymentService.getPaymentById(id);
			if (payment == null) {
				return paymentUtil.toDto(null, "payment id:" + id + " not found", "511");
			}
			return paymentUtil.toDto(payment, "getPaymentById: " + Long.toString(id), null);
		} catch (Exception e) {
			e.printStackTrace();
			return paymentUtil.toDto(null, e.toString(), "513");
		}
	}

	/**
	 * Utility: get payment(id) by notice uuid
	 *
	 * workFlow: 5
	 *
	 * ../api/payment/uuid/{uuid} GET
	 *
	 * accessible: driver
	 *
	 * @param notice_uuid
	 * @return PaymentDto
	 */
	@RequestMapping(value = "/uuid/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PaymentDto getPaymentByNoticeUuid(@PathVariable("uuid") UUID uuid) {

		logger.info("../api/payment/uuid/{uuid} GET uuid:" + uuid);

		try {
			// checkout uuid id valid
			notice = noticeService.getNoticeByUuid(uuid);
			if (notice == null) {
				return paymentUtil.toDto(null, "notice uuid:" + uuid + " invalid", "523");
			}

			// check payment and notice status are unpaid
			boolean paymentStatusEqualPaid = paymentService.paymentStatusIsByNoticeUuid("unpaid", uuid);
			boolean noticeStatusEqualPaid = noticeService.noticeStatusIsByNoticeUuid("unpaid", uuid);
			if (!paymentStatusEqualPaid || !noticeStatusEqualPaid) {
				return paymentUtil.toDto(null, "can not get payment when status is not unpaid", "535");
			}

			// update notice and payment status to "unpaid"
			notice.setStatus("unpaid");
			notice = noticeService.updateNotice(notice);
			payment = paymentService.getPaymentByNoticeId(notice.getId());
			payment.setStatus("unpaid");
			payment = paymentService.updatePayment(payment);

			return paymentUtil.toDto(payment, "getPaymentByNoticeUuid: " + uuid, null);
		} catch (Exception e) {
			e.printStackTrace();
			return paymentUtil.toDto(null, e.toString(), "513");
		}
	}

	/**
	 * Utility: paid successfully, update payment, notice, license
	 *
	 * workFlow: 6
	 *
	 * ../api/payment/{id}/paid PUT
	 *
	 * accessible: driver
	 *
	 * @param id
	 * @return LicenseDto
	 */
	@RequestMapping(value = "/{id}/paid", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public LicenseDto paidSuccessfully(@PathVariable("id") Long id) {

		logger.info("../api/payment/{id}/paid PUT id:" + id);

		try {
			// checkout payment id if valid
			payment = paymentService.getPaymentById(id);
			if (payment == null) {
				return licenseUitl.toDto(null, "payment id:" + id + " invalid", "531");
			}

			// check payment and notice status are unpaid
			notice = noticeService.getNoticeById(payment.getNoticeId());
			boolean paymentStatusEqualPaid = paymentService.paymentStatusIsByNoticeUuid("unpaid", notice.getUuid());
			boolean noticeStatusEqualPaid = noticeService.noticeStatusIsByNoticeUuid("unpaid", notice.getUuid());
			if (!paymentStatusEqualPaid || !noticeStatusEqualPaid) {
				return licenseUitl.toDto(null, "can not complete payment when status is not unpaid", "535");
			}

			// update status of notice and payment
			notice.setStatus("paid");
			notice = noticeService.updateNotice(notice);
			payment = paymentService.getPaymentByNoticeId(notice.getId());
			payment.setStatus("paid");
			payment = paymentService.updatePayment(payment);

			System.out.println(notice.toString());

			// update license
			license = licenseService.getLicenseById(notice.getLicenseId());
			license.setExpiredDate(licenseService.updateExpireDate(license, notice));
			license.setAddress(notice.getTmpAddress());
			license.setEmail(notice.getTmpEmail());
			license = licenseService.updateLicense(license);

			return licenseUitl.toDto(license, "update license", null);
		} catch (Exception e) {
			e.printStackTrace();
			return licenseUitl.toDto(null, e.toString(), "513");
		}
	}

}