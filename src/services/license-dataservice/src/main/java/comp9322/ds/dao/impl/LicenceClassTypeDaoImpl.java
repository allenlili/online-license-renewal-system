package comp9322.ds.dao.impl;

import java.io.FileReader;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import comp9322.ds.dao.LicenceClassTypeDao;
import comp9322.ds.entity.LicenceClassType;
import net.sf.saxon.xqj.SaxonXQDataSource;

public class LicenceClassTypeDaoImpl implements LicenceClassTypeDao{
	
	public LicenceClassType findByCriteria(String xqCodePath) throws Exception{
		XQDataSource ds = new SaxonXQDataSource();
		XQConnection connection = null;
		XQPreparedExpression exp = null;
		XQResultSequence result = null;
		LicenceClassType licenceClassType = null;
		try {
			connection = ds.getConnection();
			exp = connection.prepareExpression(new FileReader(xqCodePath));
			result = exp.executeQuery();
			String data = "";
	        while(result.next()){
	        		data += result.getSequenceAsString(null);
	        }
	        licenceClassType = new LicenceClassType();
	        licenceClassType.setData(data);
		} catch (Exception e) {
			throw e;
		} finally {
			 try {
				 if (result != null ) {
					 result.close();
				 }
				 if (exp != null) {
					 exp.close();
				 }
				 if (connection != null) {
					 connection.close();
				 }
			} catch (Exception e) {
				throw e;
			}
		}
		return licenceClassType;
	}
}
