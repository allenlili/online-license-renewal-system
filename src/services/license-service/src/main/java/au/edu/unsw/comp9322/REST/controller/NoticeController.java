/**
 * controller call service by url
 */
package au.edu.unsw.comp9322.REST.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.edu.unsw.comp9322.REST.dto.AddressAndEmailDto;
import au.edu.unsw.comp9322.REST.dto.NoticeDto;
import au.edu.unsw.comp9322.REST.dto.PaymentDto;
import au.edu.unsw.comp9322.REST.dto.ValidationResponse;
import au.edu.unsw.comp9322.REST.model.Notice;
import au.edu.unsw.comp9322.REST.model.Officer;
import au.edu.unsw.comp9322.REST.model.Payment;
import au.edu.unsw.comp9322.REST.service.LicenseService;
import au.edu.unsw.comp9322.REST.service.NoticeService;
import au.edu.unsw.comp9322.REST.service.OfficerService;
import au.edu.unsw.comp9322.REST.service.PaymentService;
import au.edu.unsw.comp9322.REST.util.EmailUtil;
import au.edu.unsw.comp9322.REST.util.NoticeUtil;
import au.edu.unsw.comp9322.REST.util.PaymentUtil;
import au.edu.unsw.comp9322.REST.util.SOAPValidation;
import au.edu.unsw.comp9322.REST.util.SequenceIdUtil;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

	@Autowired
	NoticeService noticeService;

	@Autowired
	SequenceIdUtil sequenceIdUtill;

	@Autowired
	Payment payment;

	@Autowired
	Notice notice;

	@Autowired
	NoticeUtil noticeUtil;

	@Autowired
	NoticeDto noticeDto;

	@Autowired
	PaymentService paymentService;

	@Autowired
	PaymentUtil paymentUtil;

	@Autowired
	Officer officer;

	@Autowired
	OfficerService officerService;

	@Autowired
	EmailUtil emailUtil;

	@Autowired
	LicenseService licenseService;

	@Autowired
	SOAPValidation soapValidation;

	private final java.util.logging.Logger logger = java.util.logging.Logger
			.getLogger(NoticeController.class.getName());

	/**
	 * Required: create notice
	 *
	 * ../api/notice POST
	 *
	 * accessible: officer
	 *
	 * @param notice
	 * @return NoticeDto
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NoticeDto createNotice(@RequestBody Notice notice) {

		logger.info("../api/notice POST notice:" + notice.toString());

		try {
			notice = noticeService.createNotice(notice);
			return noticeUtil.toDto(notice, "succeed to create notice", null);
		} catch (Exception e) {
			e.printStackTrace();
			return noticeUtil.toDto(null, e.toString(), "513");
		}
	}

	/**
	 * Required: get notice by id
	 *
	 * ../api/notice/{id} GET
	 *
	 * accessible: officer
	 *
	 * @param id
	 * @return NoticeDto
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public NoticeDto getNoticeById(@PathVariable("id") Long id) {

		logger.info("../api/notice/{id} GET id:" + id);

		try {
			notice = noticeService.getNoticeById(id);
			if (notice == null) {
				return noticeUtil.toDto(null, "notice id:" + id + " not found", "511");
			}
			return noticeUtil.toDto(notice, "getNoticeById: " + Long.toString(id), null);
		} catch (Exception e) {
			e.printStackTrace();
			return noticeUtil.toDto(null, e.toString(), "513");
		}
	}

	/**
	 * Required: get notice by uuid
	 *
	 * workFlow: 2
	 *
	 * ../api/notice/uuid/{uuid} GET
	 *
	 * accessible: driver
	 *
	 * @param uuid
	 * @return NoticeDto
	 */
	@RequestMapping(value = "/uuid/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public NoticeDto getNoticeByUuid(@PathVariable("uuid") UUID uuid) {

		logger.info("../api/notice/uuid/{uuid} GET uuid:" + uuid);

		try {
			notice = noticeService.getNoticeByUuid(uuid);

			// check uuid if valid
			if (notice == null) {
				return noticeUtil.toDto(null, "notice uuid: " + uuid + " is invalid", "523");
			}
			return noticeUtil.toDto(notice, "getNoticeByUuid: " + uuid, null);
		} catch (Exception e) {
			e.printStackTrace();
			return noticeUtil.toDto(null, e.toString(), "526");
		}
	}

	/**
	 * Required: update notice by id
	 *
	 * ../api/notice/{id} PUT
	 *
	 * accessible: officer
	 *
	 * @param id
	 * @param notice
	 * @return NoticeDto
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NoticeDto updateNoticeById(@PathVariable("id") Long id, @RequestBody Notice newNotice) {

		logger.info("../api/notice/{id} PUT id:" + id + " Notice" + newNotice.toString());

		try {
			// check id if valid
			notice = noticeService.getNoticeById(id);
			if (notice == null) {
				return noticeUtil.toDto(null, "notice id:" + id + " invalid", "521");
			}

			// update notice
			if (newNotice.getStatus().equals("manual_extend")) {
				newNotice.setIsExtend("extend");
			}
			newNotice.setId(notice.getId());
			notice = noticeService.updateNotice(newNotice);
			return noticeUtil.toDto(notice, "succeed to update notice", null);
		} catch (Exception e) {
			e.printStackTrace();
			return noticeUtil.toDto(null, e.toString(), "523");
		}
	}

	/**
	 * Required: update notice by uuid
	 * 
	 * update database after validation successful in client side, change tmp
	 * address and email, and status of notice and payment
	 *
	 * ../api/notice/uuid/{uuid} PUT
	 *
	 * accessible: driver
	 *
	 * @param uuid
	 * @param notice
	 * @return NoticeDto
	 */
	@RequestMapping(value = "/uuid/{uuid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NoticeDto updateNoticeByUuid(@PathVariable("uuid") UUID uuid, @RequestBody Notice newNotice) {

		logger.info("../api/notice/uuid/{uuid} PUT uuid:" + uuid + " Notice:" + newNotice.toString());

		try {
			// check uuid if valid
			notice = noticeService.getNoticeByUuid(uuid);
			if (notice == null) {
				return noticeUtil.toDto(null, "notice uuid:" + uuid + " invalid", "523");
			}

			if (newNotice.getStatus().equals("manual_extend")) {
				newNotice.setIsExtend("extend");
			}

			// update notice
			newNotice.setId(notice.getId());
			notice = noticeService.updateNotice(newNotice);
			// update status of payment
			payment = paymentService.getPaymentByNoticeId(notice.getId());
			payment.setStatus(notice.getStatus());
			payment = paymentService.updatePayment(payment);

			return noticeUtil.toDto(notice, "succeed to update notice", null);
		} catch (Exception e) {
			e.printStackTrace();
			return noticeUtil.toDto(null, e.toString(), "523");
		}
	}

	/**
	 * Required: archive notice by uuid(change status and delete uuid)
	 *
	 * workFlow: 10
	 *
	 * ../api/notice/uuid/{uuid}/archive GET
	 *
	 * accessible: driver
	 *
	 * @param uuid
	 * @return NoticeDto
	 */
	@RequestMapping(value = "/uuid/{uuid}/archive", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NoticeDto archiveNoticeByUuid(@PathVariable("uuid") UUID uuid) {

		logger.info("../api/notice/uuid/{uuid}/archive GET uuid:" + uuid);

		try {
			// check id if valid
			notice = noticeService.getNoticeByUuid(uuid);
			if (notice == null) {
				return noticeUtil.toDto(null, "notice uuid:" + uuid + " invalid", "523");
			}

			// check payment and notice status are paid
			// boolean correctPaymentStatus =
			// paymentService.paymentStatusIsByNoticeUuid("paid", uuid);
			// boolean correctNoticeStatus =
			// noticeService.noticeStatusIsByNoticeUuid("paid", uuid);
			// if (!correctPaymentStatus || !correctNoticeStatus) {
			// return noticeUtil.toDto(null, "can not delete notice before payment
			// complete", "514");
			// }

			// update notice: delete uuid, and upadate status of notice and payment
			notice.setUuid(null);
			notice.setStatus("archived");
			notice = noticeService.updateNotice(notice);
			payment = paymentService.getPaymentByNoticeId(notice.getId());
			payment.setStatus("archived");
			payment = paymentService.updatePayment(payment);

			return noticeUtil.toDto(notice, "succeed to archive notice", null);
		} catch (Exception e) {
			e.printStackTrace();
			return noticeUtil.toDto(null, e.toString(), "523");
		}
	}

	/**
	 * Utility: initiate renewal license notice, save notice,save payment, send
	 * email
	 *
	 * workFlow: 1
	 *
	 * ../api/notice/initiate POST
	 *
	 * accessible: officer
	 *
	 * @return list of NoticeDto
	 */
	@RequestMapping(value = "/initiate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public NoticeDto initiateRenewalNotice() {

		logger.info("../api/notice/initiate POST");

		try {
			noticeService.initiateRenewalNotice();
			return noticeUtil.toDto(null, "succeed to generate renewal notice", null);
		} catch (Exception e) {
			e.printStackTrace();
			return noticeUtil.toDto(null, e.toString(), "555");
		}
	}

	/**
	 * Utility: validate address and email, deprivative
	 *
	 * workFlow: 3
	 *
	 * ../api/notice/uuid/{uuid}/validate PUT
	 *
	 * accessible: driver
	 *
	 * @param uuid
	 * @param addressAndEmailDto:{
	 *            "preStreet":"SHOP 1,
	 *            29","streetName":"CARINYA","streetType":"AVE","suburb":"ST
	 *            MARYS","state":"NSW","email":"zlianus@gmail.com" }
	 * @return ValidationResponse: {"uuid":"" ,"address":"","addressValid":"true or
	 *         false", "email":"","emailValid":"true or false"}
	 */
	@RequestMapping(value = "/uuid/{uuid}/validate", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ValidationResponse validateAddressAndEmail(@PathVariable("uuid") UUID uuid,
			@RequestBody AddressAndEmailDto addressAndEmailDto) {
		ValidationResponse validationResponse = new ValidationResponse();

		logger.info("../api/notice/uuid/{uuid}/validate PUT uuid:" + uuid + " addressAndEmailDto:"
				+ addressAndEmailDto.toString());

		/*
		 * 
		 * deprivative
		 * 
		 */

		try {

			notice = noticeService.getNoticeByUuid(uuid);
			if (notice == null) {
				validationResponse.setReturnMsg("notice uuid:" + uuid + " invalid");
				validationResponse.setErrorCode("523");
			}

			Boolean isValidAddress = null;
			Boolean isEmail = null;
			String returnValidAddress = null;

			String preStreet = addressAndEmailDto.getPreStreet();
			String streetName = addressAndEmailDto.getStreetName();
			String streetType = addressAndEmailDto.getStreetType();
			String suburb = addressAndEmailDto.getSuburb();
			String state = addressAndEmailDto.getState();
			String email = addressAndEmailDto.getEmail();
			// check address from SOAP
			returnValidAddress = soapValidation.checkAddress(preStreet, streetName, streetType, suburb, state);
			if (returnValidAddress != null) {
				isValidAddress = true;
				notice.setTmpAddress(returnValidAddress);
			} else {
				notice.setTmpAddress(preStreet + " " + streetName + " " + streetType + ", " + suburb + ", " + state);
				isValidAddress = false;
			}

			// check email from SOAP
			if (soapValidation.checkEmail(email)) {
				isEmail = true;
			} else {
				isEmail = false;
			}
			notice.setTmpEmail(email);

			// update notice and payment status to "valid or invalid"
			if (isValidAddress && isEmail) {
				notice.setStatus("valid");
			} else {
				notice.setStatus("invalid");
			}
			notice = noticeService.updateNotice(notice);

			payment = paymentService.getPaymentByNoticeId(notice.getId());
			if (isValidAddress && isEmail) {
				payment.setStatus("valid");
			} else {
				payment.setStatus("invalid");
			}
			payment = paymentService.updatePayment(payment);

			// return
			validationResponse.setUuid(uuid);
			validationResponse.setAddress(notice.getTmpAddress());
			validationResponse.setEmail(notice.getTmpEmail());
			validationResponse.setAddressValid(isValidAddress.toString());
			validationResponse.setEmailValid(isEmail.toString());
			return validationResponse;

		} catch (Exception e) {
			e.printStackTrace();
			validationResponse.setErrorCode("500");
			return validationResponse;
		}

	}

	/**
	 * Utility: driver set manual validation, after status has changed invalid
	 *
	 * workFlow: 3.1.1
	 *
	 * ../api/notice/{notice_uuid}/manual_validate PUT
	 *
	 * accessible: driver
	 *
	 * @param uuid
	 * @return noticeDto
	 */
	@RequestMapping(value = "/uuid/{uuid}/manual_validate", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public NoticeDto setManualValidation(@PathVariable("uuid") UUID uuid) {

		logger.info("../api/notice/{notice_uuid}/manual_validate PUT uuid:" + uuid);

		try {
			// check uuid if valid
			notice = noticeService.getNoticeByUuid(uuid);
			if (notice == null) {
				return noticeUtil.toDto(null, "notice uuid:" + uuid + " invalid", "523");
			}

			// check payment and notice status are invalid
			boolean correctPaymentStatus = paymentService.paymentStatusIsByNoticeUuid("invalid", uuid);
			boolean correctNoticeStatus = noticeService.noticeStatusIsByNoticeUuid("invalid", uuid);
			if (!correctPaymentStatus || !correctNoticeStatus) {
				return noticeUtil.toDto(null, "can not set manual validation when status is not invalid", "528");
			}

			// update notice
			notice.setStatus("manual_validate");
			notice = noticeService.updateNotice(notice);
			return noticeUtil.toDto(notice, "succeed to set manual validation", null);
		} catch (Exception e) {
			e.printStackTrace();
			return noticeUtil.toDto(null, e.toString(), "523");
		}
	}

	/**
	 * Utility: driver set manual extend when status is valid
	 *
	 * workFlow: 4.2
	 *
	 * ../api/notice/uuid/{notice_uuid}/manual_extend PUT
	 *
	 * accessible: driver
	 *
	 * @param uuid
	 * @return noticeDto
	 */
	@RequestMapping(value = "/uuid/{uuid}/manual_extend", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public NoticeDto setManualExtend(@PathVariable("uuid") UUID uuid) {

		logger.info("../api/notice/uuid/{notice_uuid}/manual_extend PUT uuid:" + uuid);

		try {
			// check uuid if valid
			notice = noticeService.getNoticeByUuid(uuid);
			if (notice == null) {
				return noticeUtil.toDto(null, "notice uuid:" + uuid + " invalid", "523");
			}

			// check payment and notice status are valid
			boolean correctPaymentStatus = paymentService.paymentStatusIsByNoticeUuid("valid", uuid);
			boolean correctNoticeStatus = noticeService.noticeStatusIsByNoticeUuid("valid", uuid);
			if (!correctPaymentStatus || !correctNoticeStatus) {
				return noticeUtil.toDto(null, "can not set manual extend when status is not valid", "529");
			}

			// update notice
			notice.setIsExtend("extend");
			notice.setStatus("manual_extend");
			notice = noticeService.updateNotice(notice);
			return noticeUtil.toDto(notice, "succeed to set manual extend", null);
		} catch (Exception e) {
			e.printStackTrace();
			return noticeUtil.toDto(null, e.toString(), "523");
		}
	}

	/**
	 * Utility: driver do not want to extend
	 *
	 * workFlow: 4.3
	 *
	 * ../api/notice/uuid/{notice_uuid}/not_extend PUT
	 *
	 * accessible: driver
	 *
	 * @param uuid
	 * @return PaymentDto
	 */
	@RequestMapping(value = "/uuid/{uuid}/not_extend", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public PaymentDto notExtend(@PathVariable("uuid") UUID uuid) {

		logger.info("../api/notice/uuid/{notice_uuid}/not_extend PUT uuid:" + uuid);

		try {
			// check uuid if valid
			notice = noticeService.getNoticeByUuid(uuid);
			if (notice == null) {
				return paymentUtil.toDto(null, "notice uuid:" + uuid + " invalid", "523");
			}

			// check payment and notice status are valid
			boolean correctPaymentStatus = paymentService.paymentStatusIsByNoticeUuid("valid", uuid);
			boolean correctNoticeStatus = noticeService.noticeStatusIsByNoticeUuid("valid", uuid);
			if (!correctPaymentStatus || !correctNoticeStatus) {
				return paymentUtil.toDto(null, "can not set manual extend when status is not valid", "529");
			}

			// update notice and payment
			notice.setStatus("unpaid");
			notice = noticeService.updateNotice(notice);
			payment = paymentService.getPaymentByNoticeId(notice.getId());
			payment.setStatus("unpaid");
			payment = paymentService.updatePayment(payment);

			return paymentUtil.toDto(payment, "get payment amount", null);
		} catch (Exception e) {
			e.printStackTrace();
			return paymentUtil.toDto(null, e.toString(), "523");
		}
	}

	/**
	 * Utility: get all manual validate
	 *
	 * workFlow: 8.1
	 *
	 * ../api/notice/status/manual_validate GET
	 *
	 * accessible: officer
	 *
	 * @return list of noticeDto
	 */
	@RequestMapping(value = "/status/manual_validate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<NoticeDto> getAllManualValidationNotice() {

		logger.info("../api/notice/status/manual_validate GET");

		List<NoticeDto> noticeDtoList = new ArrayList<NoticeDto>();
		List<Notice> noticeList = new ArrayList<Notice>();

		// get manual validate notices
		noticeList = noticeService.getAllNoticeByStatus("manual_validate");
		System.out.println(noticeList.size());

		// set to DTO
		for (Notice notice : noticeList) {
			noticeDtoList.add(noticeUtil.toDto(notice, null, null));
		}

		return noticeDtoList;
	}

	/**
	 * Utility: get all manual extend
	 *
	 * workFlow: 8.2
	 *
	 * ../api/notice/status/manual_extend GET
	 *
	 * accessible: officer
	 *
	 * @return list of noticeDto
	 */
	@RequestMapping(value = "/status/manual_extend", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<NoticeDto> getAllManualExtendNotice() {

		logger.info("../api/notice/status/manual_extend GET");

		List<NoticeDto> noticeDtoList = new ArrayList<NoticeDto>();
		List<Notice> noticeList = new ArrayList<Notice>();

		// get manual extend notices
		noticeList = noticeService.getAllNoticeByStatus("manual_extend");
		System.out.println(noticeList.size());

		// set to DTO
		for (Notice notice : noticeList) {
			noticeDtoList.add(noticeUtil.toDto(notice, null, null));
		}

		return noticeDtoList;
	}

	/**
	 * Utility: claim manual notice by officer and notice uuid
	 *
	 * workFlow: 8.3
	 *
	 * ../api/notice/{notice_uuid}/officer/{officer_id} PUT
	 *
	 * accessible: officer
	 *
	 *
	 * @param notice_uuid
	 * @param officer_id
	 * @return noticeDto
	 */
	@RequestMapping(value = "uuid/{notice_uuid}/officer/{officer_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public NoticeDto claimJobByOfficerIdAndNoticeUuid(@PathVariable("notice_uuid") UUID uuid,
			@PathVariable("officer_id") Long id) {

		logger.info("../api/notice/{notice_uuid}/officer/{officer_id} PUT uuid:" + uuid);

		try {
			// check office id if valid
			officer = officerService.getOfficerById(id);
			if (officer == null) {
				return noticeUtil.toDto(null, "officer id:" + id + " invalid", "541");
			}

			notice = noticeService.getNoticeByUuid(uuid);
			if (notice == null) {
				return noticeUtil.toDto(null, "uuid: " + uuid + " invalid", "542");
			}
			// check payment and notice status are manual_validate or manual_extend
			boolean correctPaymentStatusValidate = paymentService.paymentStatusIsByNoticeUuid("manual_validate", uuid);
			boolean correctNoticeStatusValidate = noticeService.noticeStatusIsByNoticeUuid("manual_validate", uuid);
			boolean correctPaymentStatusExtend = paymentService.paymentStatusIsByNoticeUuid("manual_extend", uuid);
			boolean correctNoticeStatusExtend = noticeService.noticeStatusIsByNoticeUuid("manual_extend", uuid);
			if (!correctPaymentStatusValidate && !correctPaymentStatusExtend) {
				return noticeUtil.toDto(null, "fail to claim this job", "542");
			}
			if (!correctNoticeStatusValidate && !correctNoticeStatusExtend) {
				return noticeUtil.toDto(null, "fail to claim this job", "542");
			}

			// update notice and payment
			if (notice.getStatus() == "manual_validate") {
				notice.setStatus("claimed_validate");
				payment.setStatus("claimed_validate");
			} else if (notice.getStatus() == "manual_extend") {
				notice.setStatus("claimed_extend");
				payment.setStatus("claimed_extend");
				notice.setIsExtend("true");
			}
			notice.setOfficerId(id);
			notice = noticeService.updateNotice(notice);
			payment = paymentService.getPaymentByNoticeId(notice.getId());
			payment = paymentService.updatePayment(payment);

			return noticeUtil.toDto(notice, "succeed to claim job", null);
		} catch (Exception e) {
			e.printStackTrace();
			return noticeUtil.toDto(null, e.toString(), "523");
		}
	}

	/**
	 * Utility: get claimed manual notice by officer id
	 *
	 * workFlow: 9
	 *
	 * ../api/notice/officer/{officer_id}/claimed GET
	 *
	 * accessible: officer
	 *
	 * @param officer_id
	 * @return list of noticeDto
	 */
	@RequestMapping(value = "/officer/{officer_id}/claimed", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<NoticeDto> getClaimedNoticeByOfficerId(@PathVariable("officer_id") Long id) {

		logger.info("../api/notice/officer/{officer_id}/claimed GET id:" + id);

		List<NoticeDto> noticeDtoList = new ArrayList<NoticeDto>();
		List<Notice> noticeList = new ArrayList<Notice>();
		List<Notice> noticeListValidate = new ArrayList<Notice>();
		List<Notice> noticeListExtend = new ArrayList<Notice>();

		// check office id if valid
		officer = officerService.getOfficerById(id);
		if (officer == null) {
			noticeDtoList.add(noticeUtil.toDto(null, "officer id:" + id + " invalid", "541"));
			return noticeDtoList;
		}

		// get notice by status and officerId
		noticeListValidate = noticeService.getNoticeOfficerIdByStatus(id, "manual_validate");
		noticeListExtend = noticeService.getNoticeOfficerIdByStatus(id, "manual_extend");
		noticeList.addAll(noticeListValidate);
		noticeList.addAll(noticeListExtend);

		// set to DTO
		for (Notice notice : noticeList) {
			noticeDtoList.add(noticeUtil.toDto(notice, null, null));
		}

		return noticeDtoList;

	}

	/**
	 * Utility: officer approve notice by officer id, update notice, payment, send
	 * email
	 *
	 * workFlow: 9.1
	 *
	 * ../api/notice/uuid/{notice_uuid}/officer/officer_id/approve/amount/{amount}
	 * PUT
	 *
	 * accessible: officer
	 *
	 * @param notice_uuid
	 * @param officer_id
	 * @return noticeDto
	 */
	@RequestMapping(value = "/uuid/{notice_uuid}/officer/{officer_id}/approve/amount/{amount}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public NoticeDto approveNoticeByofficerId(@PathVariable("notice_uuid") UUID uuid,
			@PathVariable("officer_id") Long id, @PathVariable("amount") Float amount) {

		logger.info("../api/notice/uuid/{notice_uuid}/officer/officer_id/approve/amount/{amount} PUT uuid:" + uuid
				+ " id:" + id + " amount:" + amount);

		try {
			// check office id if valid
			officer = officerService.getOfficerById(id);
			if (officer == null) {
				return noticeUtil.toDto(null, "officer id:" + id + " invalid", "541");
			}

			// check uuid if valid
			notice = noticeService.getNoticeByUuid(uuid);
			if (notice == null) {
				return noticeUtil.toDto(null, "uuid: " + uuid + " invalid", "542");
			}

			// check if job claimed by officer
			if (id != noticeService.getNoticeByUuid(uuid).getOfficerId()) {
				return noticeUtil.toDto(null, "you can not update others' job", "543");
			}

			// check payment and notice status are manual_validate or manual_extend
			// boolean correctPaymentStatus =
			// paymentService.paymentStatusIsByNoticeUuid("claimed", uuid);
			// boolean correctNoticeStatus =
			// noticeService.noticeStatusIsByNoticeUuid("claimed", uuid);
			// if (!correctPaymentStatus || !correctNoticeStatus) {
			// return noticeUtil.toDto(null, "you can not update unclaimed job", "544");
			// }

			// update notice and payment
			notice.setStatus("unpaid");
			notice = noticeService.updateNotice(notice);
			payment = paymentService.getPaymentByNoticeId(notice.getId());
			payment.setStatus("unpaid");
			payment.setAmount(Float.toString(amount));
			payment = paymentService.updatePayment(payment);

			String emailAddress = licenseService.getLicenseById(notice.getLicenseId()).getEmail();
			String emailSubject = "you license renewal has been approved";
			String emailBody = "Hi, " + licenseService.getLicenseById(notice.getLicenseId()).getDriverName()
					+ "\nyour license renewal has been approved. Access to "
					+ noticeUtil.generateAccessLinkForDriver(notice) + " for next step";
			emailUtil.sendEmail(emailAddress, emailSubject, emailBody);

			return noticeUtil.toDto(notice, "succeed tp approve notice", null);
		} catch (Exception e) {
			e.printStackTrace();
			return noticeUtil.toDto(null, e.toString(), "523");
		}
	}

	/**
	 * Utility: officer reject notice by officer id, update notice, payment, send
	 * email
	 *
	 * workFlow: 9.2
	 *
	 * ../api/notice/uuid/{notice_uuid}/officer/officer_id/reject PUT
	 *
	 * accessible: officer
	 *
	 * @param notice_uuid
	 * @param officer_id
	 * @param notice
	 * @return noticeDto
	 */
	@RequestMapping(value = "/uuid/{notice_uuid}/officer/{officer_id}/reject", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NoticeDto rejectNoticeByofficerId(@PathVariable("notice_uuid") UUID uuid,
			@PathVariable("officer_id") Long id, @RequestBody Notice newNotice) {

		logger.info("../api/notice/uuid/{notice_uuid}/officer/officer_id/reject PUT uuid:" + uuid + " id:" + id
				+ " Notice:" + newNotice.toString());

		try {
			// check office id if valid
			officer = officerService.getOfficerById(id);
			if (officer == null) {
				return noticeUtil.toDto(null, "officer id:" + id + " invalid", "541");
			}

			// check uuid if valid
			notice = noticeService.getNoticeByUuid(uuid);
			if (notice == null) {
				return noticeUtil.toDto(null, "uuid: " + uuid + " invalid", "542");
			}

			// check if job claimed by officer
			if (id != noticeService.getNoticeByUuid(uuid).getOfficerId()) {
				return noticeUtil.toDto(null, "you can not update others' job", "543");
			}

			// check payment and notice status are manual_validate or manual_extend

			// boolean correctPaymentStatus =
			// paymentService.paymentStatusIsByNoticeUuid("claimed", uuid);
			// boolean correctNoticeStatus =
			// noticeService.noticeStatusIsByNoticeUuid("claimed", uuid);
			// if (!correctPaymentStatus || !correctNoticeStatus) {
			// return noticeUtil.toDto(null, "you can not update unclaimed job", "544");

			// update notice and payment
			notice.setStatus("rejected");
			notice.setRejectReason(newNotice.getRejectReason());
			notice = noticeService.updateNotice(notice);
			payment = paymentService.getPaymentByNoticeId(notice.getId());
			payment.setStatus("rejected");
			payment = paymentService.updatePayment(payment);

			String emailAddress = licenseService.getLicenseById(notice.getLicenseId()).getEmail();
			String emailSubject = "you license renewal has been rejected";
			String emailBody = "Hi, " + licenseService.getLicenseById(notice.getLicenseId()).getDriverName()
					+ "\nyour license renewal has been rejected. Access to "
					+ noticeUtil.generateAccessLinkForDriver(notice) + " for next step";
			emailUtil.sendEmail(emailAddress, emailSubject, emailBody);

			return noticeUtil.toDto(notice, "succeed tp reject notice", null);
		} catch (Exception e) {
			e.printStackTrace();
			return noticeUtil.toDto(null, e.toString(), "523");
		}
	}

}
