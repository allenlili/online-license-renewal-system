package au.edu.unsw.comp9322.REST.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.unsw.comp9322.REST.constant.Constant;
import au.edu.unsw.comp9322.REST.dto.LicenseDto;
import au.edu.unsw.comp9322.REST.model.License;

@Service
public class LicenseUtil {
	@Autowired
	LicenseUtil licenseUtil;

	public LicenseDto toDto(License license, String returnMsg, String errorCode) {
		LicenseDto licenseDto = new LicenseDto();

		// ModelMapper
		if (license == null || errorCode != null) {
			licenseDto.setReturnMsg(returnMsg);
			licenseDto.setErrorCode(errorCode);
			return licenseDto;
		}

		licenseDto.setId(license.getId());
		licenseDto.setDriverName(license.getDriverName());
		licenseDto.setAddress(license.getAddress());
		licenseDto.setLicenseNumber(license.getLicenseNumber());
		licenseDto.setLicenseClass(license.getLicenseClass());
		licenseDto.setEmail(license.getEmail());
		licenseDto.setExpiredDate(license.getExpiredDate());

		licenseDto.setReturnMsg(returnMsg);
		// licenseDto.setLink(licenseUtil.generateAccessLink(license));

		return licenseDto;
	}

	public String generateAccessLink(License license) {
		return Constant.DOMAIN_NAME + "/officer/license/" + Long.toString(license.getId());
	}

	public List<LicenseDto> ListToDto(List<License> licenseList) {
		List<LicenseDto> LicenseDtoList = new ArrayList<LicenseDto>();

		for (License license : licenseList) {
			LicenseDtoList.add(licenseUtil.toDto(license, null, null));
		}
		return LicenseDtoList;
	}
}
