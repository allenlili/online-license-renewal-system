package comp9322.ds.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class HTMLTemplate {
	private Configuration cfg;
	
	public final static String templatePath = "comp9322/ds/templates";
	
	public final static String HTML_SELECT_TEMPLATE = "<th style=\"text-align:center;\"><?></th>";
	
	public final static String HTML_THEAD_TEMPLATE = 
	        "                <th style=\"text-align:center;\">Quarter</th>\n" + 
			"                <th style=\"text-align:center;\">Postcode</th>\n" + 
			"                <th style=\"text-align:center;\">Class C</th>\n" + 
			"                <th style=\"text-align:center;\">Class LR</th>\n" + 
			"                <th style=\"text-align:center;\">Class MR</th>\n" + 
			"                <th style=\"text-align:center;\">Class HR</th>\n" + 
			"                <th style=\"text-align:center;\">Class HC</th>\n" + 
			"                <th style=\"text-align:center;\">Class MC</th>\n" + 
			"                <th style=\"text-align:center;\">Class R</th>\n" + 
			"                <th style=\"text-align:center;\">Learner</th>\n" + 
			"                <th style=\"text-align:center;\">P1</th>\n" + 
			"                <th style=\"text-align:center;\">P2</th>\n" + 
			"                <th style=\"text-align:center;\">Unrestricted</th>";
	
	public HTMLTemplate() throws IOException {
		this.cfg = new Configuration();
		try {
			String directory = XQueryTemplate.class.getClassLoader().getResource(templatePath).getPath();
			File file = new File(directory);
			cfg.setDirectoryForTemplateLoading(file);
		} catch (IOException e) {
			System.out.println("Template Loading Errors.");
			throw e;
		}
	}
	
	public void generateTemplate(Map<?, ?> root, String templateName, String xqueryCodePath) throws IOException, TemplateException{
        freemarker.template.Template template = cfg.getTemplate(templateName);
        FileWriter fileWriter = new FileWriter(xqueryCodePath);
        try {  
            template.process(root, fileWriter);
        } catch (TemplateException e) {  
        		throw e;
        }  
	}
}
