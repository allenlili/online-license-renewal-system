package au.edu.unsw.comp9322.CLIENT.bean;

import org.springframework.stereotype.Component;

@Component
public class Status {
	private String initial;
	private String valid;
	private String extension;
	private String payment;
	private String complete;
	
	public Status() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Status(String initial, String valid, String extension, String payment, String complete) {
		super();
		this.initial = initial;
		this.valid = valid;
		this.extension = extension;
		this.payment = payment;
		this.complete = complete;
	}
	public String getInitial() {
		return initial;
	}
	public void setInitial(String initial) {
		this.initial = initial;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	@Override
	public String toString() {
		return "Status [initial=" + initial + ", valid=" + valid + ", extension=" + extension + ", payment=" + payment
				+ ", complete=" + complete + "]";
	}
	
}
