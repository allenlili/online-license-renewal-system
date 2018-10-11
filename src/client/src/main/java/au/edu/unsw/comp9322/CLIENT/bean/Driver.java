package au.edu.unsw.comp9322.CLIENT.bean;

import java.util.UUID;

import org.springframework.stereotype.Component;
@Component
public class Driver {
//	private long id; // mongodb: _id
	private UUID uuid; // for access by driver
//	private String status; // flag
//	private long licenseId;
//	private String tmpAddress;
//	private String tmpEmail;
//	private long officerId;
//	private String isExtend;
//	private String rejectReason;

	public Driver() {
		super();
	}

	public Driver(UUID uuid) {
		super();
		this.uuid = uuid;
	}
	
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}

	

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public long getLicenseId() {
//		return licenseId;
//	}
//
//	public void setLicenseId(long licenseId) {
//		this.licenseId = licenseId;
//	}
//
//	public String getTmpAddress() {
//		return tmpAddress;
//	}
//
//	public void setTmpAddress(String tmpAddress) {
//		this.tmpAddress = tmpAddress;
//	}
//
//	public String getTmpEmail() {
//		return tmpEmail;
//	}
//
//	public void setTmpEmail(String tmpEmail) {
//		this.tmpEmail = tmpEmail;
//	}
//
//	public long getOfficerId() {
//		return officerId;
//	}
//
//	public void setOfficerId(long officerId) {
//		this.officerId = officerId;
//	}
//
//	public String getIsExtend() {
//		return isExtend;
//	}
//
//	public void setIsExtend(String isExtend) {
//		this.isExtend = isExtend;
//	}
//
//	public String getRejectReason() {
//		return rejectReason;
//	}
//
//	public void setRejectReason(String rejectReason) {
//		this.rejectReason = rejectReason;
//	}

}
