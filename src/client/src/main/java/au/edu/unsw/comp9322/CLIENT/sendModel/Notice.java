package au.edu.unsw.comp9322.CLIENT.sendModel;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Notice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id; // mongodb: _id
	private UUID uuid; // for access by driver
	private String status; // flag
	private long licenseId;
	private String tmpAddress;
	private String tmpEmail;
	private long officerId;
	private String rejectReason;
	private String isExtend;
	
	public String getIsExtend() {
		return isExtend;
	}


	public void setIsExtend(String isExtend) {
		this.isExtend = isExtend;
	}
	public Notice() {
		super();
	}

	public Notice(long id, UUID uuid, String status, long licenseId, String tmpAddress, String tmpEmail, long officerId, String isExtend) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.status = status;
		this.licenseId = licenseId;
		this.tmpAddress = tmpAddress;
		this.tmpEmail = tmpEmail;
		this.officerId = officerId;
		this.isExtend = isExtend;
	}
	public static Notice CreateTestNotice() {
		Notice notice = new Notice();
	    notice.setUuid(UUID.fromString("ed1e4530-85f1-46bb-891a-c2d2819e9d72"));
	    notice.setStatus("paid");
	    notice.setLicenseId(8);
	    notice.setTmpAddress("11 Tallowood Court, Goonellabah, NSW");
	    notice.setTmpEmail("youremail@email.com");
	    notice.setOfficerId(0);
	    return notice;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(long licenseId) {
		this.licenseId = licenseId;
	}

	public String getTmpAddress() {
		return tmpAddress;
	}

	public void setTmpAddress(String tmpAddress) {
		this.tmpAddress = tmpAddress;
	}

	public String getTmpEmail() {
		return tmpEmail;
	}

	public void setTmpEmail(String tmpEmail) {
		this.tmpEmail = tmpEmail;
	}

	public long getOfficerId() {
		return officerId;
	}

	public void setOfficerId(long officerId) {
		this.officerId = officerId;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	@Override
	public String toString() {
		return "{\n" 
				+ "    \"uuid\" : \""+ uuid +"\",\n"
				+ "    \"status\" : \""+ status +"\",\n" 
				+ "    \"licenseId\" : "+ licenseId +",\n"
				+ "    \"tmpAddress\" : \""+ tmpAddress +"\",\n"
				+ "    \"tmpEmail\" : \""+ tmpEmail +"\",\n" 
				+ "    \"officerId\" : "+officerId+"\n" 
				+ "}";
	}
	
	

}
