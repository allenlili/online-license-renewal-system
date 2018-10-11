package comp9322.ds;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;
import comp9322.ds.util.HTMLBuilder;
import comp9322.ds.util.XQueryBulider;
import comp9322.ds.util.XQueryTemplate;

public class UtilTest {
	@Test
	public void testcase01() {
		String selectStr = XQueryBulider.buildSelect("Class C,Class LR, Learner, Unrestricted");
		System.out.println(selectStr);
		String htmlSelectStr = HTMLBuilder.buildHTMLHeadSelect("Class C,Class LR, Learner, Unrestricted");
		System.out.println(htmlSelectStr);
		String whereStr = XQueryBulider.buildWhere("quarter eq q1 and postcode eq 2000 or postcode gt 2000 and postcode != 2000");
		System.out.println(whereStr);
		String orderbyStr = XQueryBulider.buildOrderby("Class C, Learner");
		System.out.println(orderbyStr);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testcase02() throws IOException {
		String select = XQueryBulider.buildSelect("Class C,Class LR, Learner, Unrestricted");
		System.out.println(select);
		String htmlSelect = HTMLBuilder.buildHTMLHeadSelect("Class C,Class LR, Learner, Unrestricted");
		System.out.println(htmlSelect);
		String where = XQueryBulider.buildWhere("quarter eq q1");
		System.out.println(where);
		String orderby = XQueryBulider.buildOrderby("Class C");
		System.out.println(orderby);
		XQueryTemplate template = new XQueryTemplate();
		Map root = new HashMap();
        root.put("select", select);  
        root.put("where", where);
        root.put("orderby", orderby);
		template.generateTemplate(root, "xq.ftl", UUID.randomUUID().toString().substring(0, 8));
		root.clear();
		root = new HashMap();
        root.put("select", select);  
        root.put("where", where);
        root.put("orderby", orderby);
        root.put("htmlSelect", htmlSelect);
		template.generateTemplate(root, "htmlxq.ftl", UUID.randomUUID().toString().substring(0, 8)+"html");
	}
}
