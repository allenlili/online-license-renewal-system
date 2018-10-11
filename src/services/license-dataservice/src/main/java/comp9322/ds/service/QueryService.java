package comp9322.ds.service;

import comp9322.ds.entity.LicenceClassType;

public interface QueryService {
	public LicenceClassType queryLicenceClassTypeReturnXML(String select, String where, String orderby) throws Exception;
	public LicenceClassType queryLicenceClassTypeReturnHTML(String select, String where, String orderby) throws Exception;
	
	public String errorHandle(String str) throws Exception;
}
