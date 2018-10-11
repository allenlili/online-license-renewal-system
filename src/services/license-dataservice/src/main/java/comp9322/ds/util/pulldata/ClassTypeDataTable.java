//package comp9322.ds.util.pulldata;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import org.jdom2.Document;
//import org.jdom2.output.Format;
//import org.jdom2.output.XMLOutputter;
//
//import com.jaunt.Element;
//import com.jaunt.Elements;
//import com.jaunt.JauntException;
//import com.jaunt.UserAgent;
//import com.jaunt.component.Table;
//
//import comp9322.ds.dao.DataSourceLink;
//
//public class ClassTypeDataTable {
//	public static Document getClassTypeByQuarter(String quarter, String classlink, String typelink, Document doc, org.jdom2.Element rootElement) {
//		System.out.println(classlink);
//		System.out.println(typelink);
//		int counter = 2;
//		try{
//			UserAgent userAgent = new UserAgent();
//			userAgent.visit(classlink);
//			Table table = userAgent.doc.getTable("<table class=table >");
//	  
//			UserAgent userAgent2 = new UserAgent();
//			userAgent2.visit(typelink);
//			Table table2 = userAgent2.doc.getTable("<table class=table >");
//			
//			org.jdom2.Element rowElement;
//			while(true) {
//				Elements elements = table.getRow(counter);
//				Elements elements2 = table2.getRow(counter);
//				
//				rowElement = new org.jdom2.Element("row");
//				//rowElement.setAttribute("quarter", str);
//				org.jdom2.Element columnElement;
//				columnElement = new org.jdom2.Element("column");
//				columnElement.setAttribute("name", "quarter");
//				columnElement.setText(quarter);
//				rowElement.addContent(columnElement);
//				for (int i = 0; i < elements.size(); i++) {
//					Element element = elements.getElement(i);
//					if (i == 1) {
//						continue;
//					} else if (i == 0) {
//						columnElement = new org.jdom2.Element("column");
//						columnElement.setAttribute("name", "postcode");
//						columnElement.setText(element.innerText().trim().replaceAll(",", ""));
//						rowElement.addContent(columnElement);
//						//rowElement.setAttribute("postcode", element.innerText().trim().replaceAll(",", ""));
//					} else if (i == 2) {
//						columnElement = new org.jdom2.Element("column");
//						columnElement.setAttribute("name", "Class C");
//						columnElement.setText(element.innerText().trim().replaceAll(",", ""));
//						rowElement.addContent(columnElement);
//					} else if (i == 3) {
//						columnElement = new org.jdom2.Element("column");
//						columnElement.setAttribute("name", "Class LR");
//						columnElement.setText(element.innerText().trim().replaceAll(",", ""));
//						rowElement.addContent(columnElement);
//					} else if (i == 4) {
//						columnElement = new org.jdom2.Element("column");
//						columnElement.setAttribute("name", "Class MR");
//						columnElement.setText(element.innerText().trim().replaceAll(",", ""));
//						rowElement.addContent(columnElement);
//					} else if (i == 5) {
//						columnElement = new org.jdom2.Element("column");
//						columnElement.setAttribute("name", "Class HR");
//						columnElement.setText(element.innerText().trim().replaceAll(",", ""));
//						rowElement.addContent(columnElement);
//					} else if (i == 6) {
//						columnElement = new org.jdom2.Element("column");
//						columnElement.setAttribute("name", "Class LC");
//						columnElement.setText(element.innerText().trim().replaceAll(",", ""));
//						rowElement.addContent(columnElement);
//					} else if (i == 7) {
//						columnElement = new org.jdom2.Element("column");
//						columnElement.setAttribute("name", "Class MC");
//						columnElement.setText(element.innerText().trim().replaceAll(",", ""));
//						rowElement.addContent(columnElement);
//					} else if (i == 8) {
//						columnElement = new org.jdom2.Element("column");
//						columnElement.setAttribute("name", "Class R");
//						columnElement.setText(element.innerText().trim().replaceAll(",", ""));
//						rowElement.addContent(columnElement);
//					} else {
//					   
//					}
//					System.out.print(element.innerText().trim().replaceAll(",", ""));
//					System.out.print(", ");
//				}
//				for (int i = 0; i < elements2.size(); i++) {
//					Element element2 = elements2.getElement(i);
//					if (i <= 1) {
//						continue;
//					}
//					if (i == 2) {
//						columnElement = new org.jdom2.Element("column");
//						columnElement.setAttribute("name", "Learner");
//						columnElement.setText(element2.innerText().trim().replaceAll(",", ""));
//						rowElement.addContent(columnElement);
//					} else if (i == 3) {
//						columnElement = new org.jdom2.Element("column");
//						columnElement.setAttribute("name", "P1");
//						columnElement.setText(element2.innerText().trim().replaceAll(",", ""));
//						rowElement.addContent(columnElement);
//					} else if (i == 4) {
//						columnElement = new org.jdom2.Element("column");
//						columnElement.setAttribute("name", "P2");
//						columnElement.setText(element2.innerText().trim().replaceAll(",", ""));
//						rowElement.addContent(columnElement);
//					} else if (i == 5) {
//						columnElement = new org.jdom2.Element("column");
//						columnElement.setAttribute("name", "Unrestricted");
//						columnElement.setText(element2.innerText().trim().replaceAll(",", ""));
//						rowElement.addContent(columnElement);
//					} else {
//						
//					}
//					System.out.print(element2.innerText().trim().replaceAll(",", ""));
//					System.out.print(", ");
//				}
//				System.out.println();
//				counter++;
//				rootElement.addContent(rowElement);
//			}
//	   	} catch(JauntException e){
//		   System.err.println(e);
//	   	} catch (ArrayIndexOutOfBoundsException e) {
//		   System.out.println("end!");
//	   	}
//		return doc;
//	}
//	
//	public static void getClassType() {
//		Document doc = new Document();
//		org.jdom2.Element rootElement = new org.jdom2.Element("table");
//		rootElement.setAttribute("name", "class-and-type");
//		doc.setRootElement(rootElement);
//		getClassTypeByQuarter("1", DataSourceLink.LICENCE_CLASS_Q1, DataSourceLink.PRIMARY_LICENCE_TYPE_Q1, doc, doc.getRootElement());
//		getClassTypeByQuarter("2", DataSourceLink.LICENCE_CLASS_Q2, DataSourceLink.PRIMARY_LICENCE_TYPE_Q2, doc, doc.getRootElement());
//		getClassTypeByQuarter("3", DataSourceLink.LICENCE_CLASS_Q3, DataSourceLink.PRIMARY_LICENCE_TYPE_Q3, doc, doc.getRootElement());
//		getClassTypeByQuarter("4", DataSourceLink.LICENCE_CLASS_Q4, DataSourceLink.PRIMARY_LICENCE_TYPE_Q4, doc, doc.getRootElement());
//		Format format = Format.getPrettyFormat();
//		format.setIndent("  ").setEncoding("utf-8");
//		XMLOutputter outputter = new XMLOutputter(format);
//		try {
//			FileOutputStream fileOutputStream = new FileOutputStream("src/main/resource/comp9322/ds/data/licence_class_type_table.xml");
//		   	outputter.output(doc, fileOutputStream);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void main(String[] args) {
//		getClassType();
//	}
//}
