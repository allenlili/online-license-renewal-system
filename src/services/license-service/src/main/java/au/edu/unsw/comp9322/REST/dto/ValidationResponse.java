package au.edu.unsw.comp9322.REST.dto;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class ValidationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UUID uuid;
	private String address;
	private String addressValid;
	private String email;
	private String emailValid;

	private String returnMsg;
	private String errorCode;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressValid() {
		return addressValid;
	}

	public void setAddressValid(String addressValid) {
		this.addressValid = addressValid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailValid() {
		return emailValid;
	}

	public void setEmailValid(String emailValid) {
		this.emailValid = emailValid;
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

	@Override
	public String toString() {
		return "ValidationResponse [uuid=" + uuid + ", address=" + address + ", addressValid=" + addressValid
				+ ", email=" + email + ", emailValid=" + emailValid + ", returnMsg=" + returnMsg + ", errorCode="
				+ errorCode + "]";
	}

}
