package au.edu.unsw.comp9322.CLIENT.responseModel;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class LicenseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String returnMsg;
	private String errorCode;
	private String link;

	// License model
	private long id;
	private String driverName;
	private String address;
	private String licenseNumber;
	private String licenseClass;
	private String email;
	private String expiredDate;

	public LicenseDto() {
		super();
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getLicenseClass() {
		return licenseClass;
	}

	public void setLicenseClass(String licenseClass) {
		this.licenseClass = licenseClass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	@Override
	public String toString() {
		return "LicenseDto [returnMsg=" + returnMsg + ", errorCode=" + errorCode + ", link=" + link + ", id=" + id
				+ ", driverName=" + driverName + ", address=" + address + ", licenseNumber=" + licenseNumber
				+ ", licenseClass=" + licenseClass + ", email=" + email + ", expiredDate=" + expiredDate + "]";
	}

}
