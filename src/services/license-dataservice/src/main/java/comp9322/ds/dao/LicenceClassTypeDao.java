package comp9322.ds.dao;


import comp9322.ds.entity.LicenceClassType;


public interface LicenceClassTypeDao {
	
	public LicenceClassType findByCriteria(String xqCodePath) throws Exception;
	
	
}
