package au.edu.unsw.comp9322.REST.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class PaymentDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String returnMsg;
	private String errorCode;
	private String link;

	// license model
	private long id;
	private String status;
	private String amount;
	private String initialDate;
	private String paidDate;
	private long noticeId;

	public PaymentDto() {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}

	public String getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}

	public long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(long noticeId) {
		this.noticeId = noticeId;
	}

	@Override
	public String toString() {
		return "PaymentDto [returnMsg=" + returnMsg + ", errorCode=" + errorCode + ", link=" + link + ", id=" + id
				+ ", status=" + status + ", amount=" + amount + ", initialDate=" + initialDate + ", paidDate="
				+ paidDate + ", noticeId=" + noticeId + "]";
	}

}
