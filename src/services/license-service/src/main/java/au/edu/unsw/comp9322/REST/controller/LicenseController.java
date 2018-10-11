package au.edu.unsw.comp9322.REST.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import au.edu.unsw.comp9322.REST.dto.LicenseDto;
import au.edu.unsw.comp9322.REST.model.License;
import au.edu.unsw.comp9322.REST.service.LicenseService;
import au.edu.unsw.comp9322.REST.service.NoticeService;
import au.edu.unsw.comp9322.REST.service.PaymentService;
import au.edu.unsw.comp9322.REST.util.LicenseUtil;

@RestController
@RequestMapping("/api/license")
public class LicenseController {
	@Autowired
	License license;
	@Autowired
	LicenseService licenseService;
	@Autowired
	LicenseUtil licenseUtil;
	@Autowired
	PaymentService paymentService;
	@Autowired
	NoticeService noticeService;

	private final java.util.logging.Logger logger = java.util.logging.Logger
			.getLogger(NoticeController.class.getName());

	/**
	 * Required: get license by licenseId
	 * 
	 * ../api/license/{id} GET
	 * 
	 * accessible: officer
	 * 
	 * @param id
	 * @return LicenseDto
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public LicenseDto getLicenseById(@PathVariable("id") long id) {

		logger.info("../api/license/{id} GET id:" + id);

		try {
			license = licenseService.getLicenseById(id);
			if (license == null) {
				return licenseUtil.toDto(null, "license id:" + id + " not found", "511");
			}
			return licenseUtil.toDto(license, "getLicenseById: " + Long.toString(id), null);
		} catch (Exception e) {
			e.printStackTrace();
			return licenseUtil.toDto(null, e.toString(), "513");
		}

	}

	/**
	 * Required: update license after renewal process complete
	 * 
	 * ../api/license/{id} PUT
	 * 
	 * accessible: driver
	 * 
	 * @param id
	 * @param license
	 * @return LicenseDto
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public LicenseDto updateLicenseById(@PathVariable("id") Long id, @RequestBody License newLicense) {

		logger.info("../api/license/{id} PUT License:" + newLicense.toString());

		try {
			// check id if valid
			license = licenseService.getLicenseById(id);
			if (license == null) {
				return licenseUtil.toDto(null, "license id:" + id + " invalid", "511");
			}

			// check payment and notice status are paid
			boolean paymentStatusEqualPaid = paymentService.paymentStatusIsPaidByLicenseId(id);
			boolean noticeStatusEqualPaid = noticeService.noticeStatusIsPaidByLicenseId(id);
			if (!paymentStatusEqualPaid || !noticeStatusEqualPaid) {
				return licenseUtil.toDto(null, "can not update license before payment complete", "512");
			}

			// update license
			newLicense.setId(license.getId());
			license = licenseService.updateLicense(newLicense);
			return licenseUtil.toDto(license, "success to update license", null);
		} catch (Exception e) {
			e.printStackTrace();
			return licenseUtil.toDto(null, e.toString(), "513");
		}

	}

	/**
	 * Utility: Officer get all renewable license
	 * 
	 * ../api/license/Renewable GET
	 * 
	 * @return List of LicenseDto
	 */
	@RequestMapping(value = "/Renewable", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LicenseDto> getRenewableLicenses() {

		logger.info("../api/license/Renewable GET");

		List<LicenseDto> LicenseDtos = new ArrayList<LicenseDto>();
		List<License> licenses = new ArrayList<License>();
		licenses = licenseService.getRenewableLicenses();

		for (License license : licenses) {
			LicenseDtos.add(licenseUtil.toDto(license, null, null));
		}

		return LicenseDtos;
	}
}
