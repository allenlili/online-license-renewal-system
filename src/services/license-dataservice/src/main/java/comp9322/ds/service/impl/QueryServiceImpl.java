package comp9322.ds.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import comp9322.ds.dao.LicenceClassTypeDao;
import comp9322.ds.dao.impl.LicenceClassTypeDaoImpl;
import comp9322.ds.entity.LicenceClassType;
import comp9322.ds.service.QueryService;
import comp9322.ds.util.HTMLBuilder;
import comp9322.ds.util.HTMLTemplate;
import comp9322.ds.util.XQueryBulider;
import comp9322.ds.util.XQueryTemplate;

public class QueryServiceImpl implements QueryService{
	private LicenceClassTypeDao licenceClassTypeDao;
	
	public QueryServiceImpl() {
		if (licenceClassTypeDao == null) {
			licenceClassTypeDao = new LicenceClassTypeDaoImpl();
		}
	}
	
	public LicenceClassType queryLicenceClassTypeReturnXML(String select, String where, String orderby) throws Exception {
		String uuid = UUID.randomUUID().toString();
		String selectClause = XQueryBulider.buildSelect(select);
		String whereClause = XQueryBulider.buildWhere(where);
		String orderbyClause = XQueryBulider.buildOrderby(orderby);
		XQueryTemplate queryTemplate = new XQueryTemplate();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<String, String> root = new HashMap();
		root.put("dataPath", QueryServiceImpl.class.getClassLoader().getResource("comp9322/ds/data/licence_class_type_table.xml").getPath());
		root.put("select", selectClause);
		root.put("where", whereClause);
		root.put("orderby", orderbyClause);
		String directory = QueryServiceImpl.class.getClassLoader().getResource("comp9322/ds/data").getPath();
		String xqueryCodePath = directory + uuid + XQueryTemplate.XQUERY_FILE_SUFFIX;
		queryTemplate.generateTemplate(root, "xq.ftl", xqueryCodePath);
		LicenceClassType licenceClassType = licenceClassTypeDao.findByCriteria(xqueryCodePath);
		return licenceClassType;
	}

	@Override
	public LicenceClassType queryLicenceClassTypeReturnHTML(String select, String where, String orderby) throws Exception {
		String uuid = UUID.randomUUID().toString();
		String selectClause = XQueryBulider.buildSelect(select);
		String whereClause = XQueryBulider.buildWhere(where);
		String orderbyClause = XQueryBulider.buildOrderby(orderby);
		String htmlSelectClause = HTMLBuilder.buildHTMLHeadSelect(select);
		XQueryTemplate queryTemplate = new XQueryTemplate();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<String, String> root = new HashMap();
		root.put("dataPath", QueryServiceImpl.class.getClassLoader().getResource("comp9322/ds/data/licence_class_type_table.xml").getPath());
		root.put("htmlSelect", htmlSelectClause);
		root.put("select", selectClause);
		root.put("where", whereClause);
		root.put("orderby", orderbyClause);
		String directory = QueryServiceImpl.class.getClassLoader().getResource("comp9322/ds/data").getPath();
		String xqueryCodePath = directory + uuid + "html" + XQueryTemplate.XQUERY_FILE_SUFFIX;
		queryTemplate.generateTemplate(root, "htmlxq.ftl", xqueryCodePath);
		LicenceClassType licenceClassType = licenceClassTypeDao.findByCriteria(xqueryCodePath);
		return licenceClassType;
	}
	
	public String errorHandle(String str) throws Exception {
		String uuid = UUID.randomUUID().toString();
		String directory = QueryServiceImpl.class.getClassLoader().getResource("comp9322/ds/data").getPath();
		String imagePath = "https://scontent.fcbr1-1.fna.fbcdn.net/v/t34.0-0/p280x280/21074335_10157185376644988_1303904052_n.gif?fallback=1&oh=7a825bfd6889be9370687d90f1a5e0f5&oe=59E90A34";
		String errorHtmlContentPath = directory + uuid + "error.html";
		HTMLTemplate htmlTemplate = new HTMLTemplate();
		Map<String, String> root = new HashMap<String, String>();
		root.put("status", str);
		root.put("imagePath", imagePath);
		htmlTemplate.generateTemplate(root, "error.ftl", errorHtmlContentPath);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(errorHtmlContentPath)));
		String line = null;
		StringBuffer stringBuffer = new StringBuffer();
		while((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}
		return stringBuffer.toString();
	} 
}
