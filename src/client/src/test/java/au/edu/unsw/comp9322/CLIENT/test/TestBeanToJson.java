package au.edu.unsw.comp9322.CLIENT.test;

import java.util.UUID;

import au.edu.unsw.comp9322.CLIENT.sendModel.Notice;

public class TestBeanToJson {
	public static void main(String[] args) {
	
		Notice notice = new Notice();
	    notice.setUuid(UUID.fromString("ed1e4530-85f1-46bb-891a-c2d2819e9d72"));
	    notice.setStatus("paid");
	    notice.setLicenseId(8);
	    notice.setTmpAddress("11 Tallowood Court, Goonellabah, NSW");
	    notice.setTmpEmail("youremail@email.com");
	    notice.setOfficerId(0);
	    System.out.println(notice.toString());
	    
	    String input = "{\n" + "    \"uuid\" : \"ed1e4530-85f1-46bb-891a-c2d2819e9d72\",\n"
				+ "    \"status\" : \"paid\",\n" + "    \"licenseId\" : 8,\n"
				+ "    \"tmpAddress\" : \"11 Tallowood Court, Goonellabah, NSW\",\n"
				+ "    \"tmpEmail\" : \"youremail@email.com\",\n" + "    \"officerId\" : 0\n" + "}";
	    
	    System.out.println(input);
	}   
}
