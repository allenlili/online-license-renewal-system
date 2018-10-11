package au.edu.unsw.comp9322.CLIENT.bean;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class MyOfficer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String password;
	
	public MyOfficer() {
		super();
	}

	public MyOfficer(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Officer [id=" + id + ", name=" + name + ", password=" + password + "]";
	}


	

}