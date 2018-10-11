package comp9322.ds.util;

import java.util.ArrayList;
import java.util.List;

public class HTMLBuilder {
	public static String buildHTMLHeadSelect(String select) {
		if (select == null) {
			return HTMLTemplate.HTML_THEAD_TEMPLATE;
		}
		List<String> colList = new ArrayList<String>();
		String[] cols = select.split(",");
		for (String string : cols) {
			colList.add(string.trim());
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < colList.size(); i++) {
			String newone = HTMLTemplate.HTML_SELECT_TEMPLATE;
			newone = newone.replaceAll("<\\?>", colList.get(i));
			if (i == colList.size() - 1) {
				buffer.append("\t\t\t\t" + newone);
				break;
			} else {
				buffer.append("\t\t\t\t" + newone + "\n");
			}
		}
		return buffer.toString();
	}
}
