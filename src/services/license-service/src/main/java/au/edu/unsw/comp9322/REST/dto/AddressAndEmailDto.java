package au.edu.unsw.comp9322.REST.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class AddressAndEmailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String preStreet;
	private String streetName;
	private String streetType;
	private String suburb;
	private String state;
	private String email;

	public String getPreStreet() {
		return preStreet;
	}

	public void setPreStreet(String preStreet) {
		this.preStreet = preStreet;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetType() {
		return streetType;
	}

	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
