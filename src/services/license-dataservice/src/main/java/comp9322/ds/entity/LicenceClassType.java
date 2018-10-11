package comp9322.ds.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class LicenceClassType {
	private String data;
	
	public String getData() {
		return data;
	}
	
	@XmlElement
	public void setData(String data) {
		this.data = data;
	}
	
}
