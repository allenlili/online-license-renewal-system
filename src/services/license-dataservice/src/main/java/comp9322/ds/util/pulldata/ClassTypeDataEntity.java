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
//public class ClassTypeDataEntity {
//	// Class C, Class LR, Class MR, Class HR, Class HC, Class MC, Class R, Learner, P1, P2, Unrestricted
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
//			org.jdom2.Element classTypeElement;
//			org.jdom2.Element postcodeElement;
//			org.jdom2.Element quarterElement;
//			while(true) {
//				Elements elements = table.getRow(counter);
//				Elements elements2 = table2.getRow(counter);
//				
//				classTypeElement = new org.jdom2.Element("Entity");
//				classTypeElement.setAttribute("name", "class-and-type");
//				
//				postcodeElement = new org.jdom2.Element("postcode");
//				
//				quarterElement = new org.jdom2.Element("quarter");
//				quarterElement.setText(quarter);
//				classTypeElement.addContent(quarterElement);
//				
//				for (int i = 0; i < elements.size(); i++) {
//					Element element = elements.getElement(i);
//					if (i == 1) {
//						continue;
//					} else if (i == 0) {
//						postcodeElement.setText(element.innerText().trim().replaceAll(",", ""));
//						classTypeElement.addContent(postcodeElement);
//					} else if (i == 2) {
//						org.jdom2.Element classCElement = new org.jdom2.Element("C");
//						classCElement.setText(element.innerText().trim().replaceAll(",", ""));
//						classCElement.setAttribute("name", "Class C");
//						classTypeElement.addContent(classCElement);
//					} else if (i == 3) {
//						org.jdom2.Element classLRElement = new org.jdom2.Element("LR");
//						classLRElement.setText(element.innerText().trim().replaceAll(",", ""));
//						classLRElement.setAttribute("name", "Class LR");
//						classTypeElement.addContent(classLRElement);
//					} else if (i == 4) {
//					   	org.jdom2.Element classMRElement = new org.jdom2.Element("MR");
//					   	classMRElement.setText(element.innerText().trim().replaceAll(",", ""));
//					   	classMRElement.setAttribute("name", "Class MR");
//						classTypeElement.addContent(classMRElement);
//					} else if (i == 5) {
//					   	org.jdom2.Element classHRElement = new org.jdom2.Element("HR");
//					   	classHRElement.setText(element.innerText().trim().replaceAll(",", ""));
//					   	classHRElement.setAttribute("name", "Class HR");
//						classTypeElement.addContent(classHRElement);
//					} else if (i == 6) {
//						org.jdom2.Element classHCElement = new org.jdom2.Element("HC");
//					   	classHCElement.setText(element.innerText().trim().replaceAll(",", ""));
//					   	classHCElement.setAttribute("name", "Class HC");
//						classTypeElement.addContent(classHCElement);
//					} else if (i == 7) {
//						org.jdom2.Element classMCElement = new org.jdom2.Element("MC");
//						classMCElement.setText(element.innerText().trim().replaceAll(",", ""));
//						classMCElement.setAttribute("name", "Class MC");
//						classTypeElement.addContent(classMCElement);
//					} else if (i == 8) {
//						org.jdom2.Element classRElement = new org.jdom2.Element("R");
//						classRElement.setText(element.innerText().trim().replaceAll(",", ""));
//						classRElement.setAttribute("name", "Class R");
//						classTypeElement.addContent(classRElement);
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
//						org.jdom2.Element learnerElement = new org.jdom2.Element("learner");
//						learnerElement.setText(element2.innerText().trim().replaceAll(",", ""));
//						learnerElement.setAttribute("name", "Learner");
//						classTypeElement.addContent(learnerElement);
//					} else if (i == 3) {
//						org.jdom2.Element p1Element = new org.jdom2.Element("p1");
//						p1Element.setText(element2.innerText().trim().replaceAll(",", ""));
//						p1Element.setAttribute("name", "P1");
//						classTypeElement.addContent(p1Element);
//					} else if (i == 4) {
//						org.jdom2.Element p2Element = new org.jdom2.Element("p2");
//						p2Element.setText(element2.innerText().trim().replaceAll(",", ""));
//						p2Element.setAttribute("name", "P2");
//						classTypeElement.addContent(p2Element);
//					} else if (i == 5) {
//						org.jdom2.Element unrestrictedElement = new org.jdom2.Element("unrestricted");
//						unrestrictedElement.setText(element2.innerText().trim().replaceAll(",", ""));
//						unrestrictedElement.setAttribute("name", "Unrestricted");
//						classTypeElement.addContent(unrestrictedElement);
//					} else {
//						
//					}
//					System.out.print(element2.innerText().trim().replaceAll(",", ""));
//					System.out.print(", ");
//				}
//				System.out.println();
//				counter++;
//				rootElement.addContent(classTypeElement);
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
//		org.jdom2.Element rootElement = new org.jdom2.Element("Entities");
//		rootElement.setAttribute("name", "class-and-type-list");
//		doc.setRootElement(rootElement);
//		getClassTypeByQuarter("1", DataSourceLink.LICENCE_CLASS_Q1, DataSourceLink.PRIMARY_LICENCE_TYPE_Q1, doc, doc.getRootElement());
//		getClassTypeByQuarter("2", DataSourceLink.LICENCE_CLASS_Q2, DataSourceLink.PRIMARY_LICENCE_TYPE_Q2, doc, doc.getRootElement());
//		getClassTypeByQuarter("3", DataSourceLink.LICENCE_CLASS_Q3, DataSourceLink.PRIMARY_LICENCE_TYPE_Q3, doc, doc.getRootElement());
//		getClassTypeByQuarter("4", DataSourceLink.LICENCE_CLASS_Q4, DataSourceLink.PRIMARY_LICENCE_TYPE_Q4, doc, doc.getRootElement());
//		Format format = Format.getPrettyFormat();
//		format.setIndent("  ").setEncoding("utf-8");
//		XMLOutputter outputter = new XMLOutputter(format);
//		try {
//			FileOutputStream fileOutputStream = new FileOutputStream("src/main/resource/comp9322/ds/data/licence_class_type_entity.xml");
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
