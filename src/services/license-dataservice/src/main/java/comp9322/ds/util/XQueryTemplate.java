package comp9322.ds.util;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class XQueryTemplate{
	private Configuration cfg;
	
	public final static String templatePath = "comp9322/ds/templates";
	
	public final static String SELECT_TEMPLATE = "@name = '<?>'";
	public final static String SELECT_TEMPLATE2 = "{$row/child::column[@name = '<?>']}";
	public final static String SELECT_REGEX = "<\\?>";
	public final static String SELECT_TEMPLATE_FULL = "$row/child::*";
	public final static String SELECT_TEMPLATE_FULL2 = "{$row/child::*}";
	
	public final static String WHERE_TEMPLATE_INT = "$row[xs:integer(child::column[@name = '<l>']/text()) <op> <r>]";
	public final static String WHERE_TEMPLATE_STR = "$row[child::column[@name = '<l>']/text() <op> '<r>']";
	public final static String WHERE_LEFT_REGEX = "<l>";
	public final static String WHERE_OP_REGEX = "<op>";
	public final static String WHERE_RIGHT_REGEX = "<r>";
	
	public final static String ORDERBY_TEMPLATE_INT = "xs:integer($row/column[@name = '<?>']/text())";
	public final static String ORDERBY_TEMPLATE_STR = "$row/column[@name = '<?>']/text()";
	public final static String ORDERBY_REGEX = "<\\?>";

	public final static String XQUERY_FILE_SUFFIX = ".xq";
	
	public final static String OR = "or";
	
	public final static String EMPTY = "";
	
	
	public static String generateSelectTemplate(String params) {
		return "$row/child::column[" + params + "]";
	}
	
	public static String generateWhereTemplate(String params) {
		return "where " + params;
	}
	
	public static String generateOrderByTemplate(String params) {
		return "order by " + params;
	}
	
	public XQueryTemplate() {
		this.cfg = new Configuration();
		try {
			String directory = XQueryTemplate.class.getClassLoader().getResource(templatePath).getPath();
			File file = new File(directory);
			cfg.setDirectoryForTemplateLoading(file);
		} catch (IOException e) {
			System.out.println("Template Loading Errors.");
		}
	}
	
	public void generateTemplate(Map<?, ?> root, String templateName, String xqueryCodePath) throws IOException{
        freemarker.template.Template template = cfg.getTemplate(templateName);
        FileWriter fileWriter = new FileWriter(xqueryCodePath);
        try {  
            template.process(root, fileWriter);
        } catch (TemplateException e) {  
            e.printStackTrace();  
        }  
	}
	
}
