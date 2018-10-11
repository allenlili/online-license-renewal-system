package comp9322.ds.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XQueryBulider {
		
	/**
	 * e.g. select = Class C,Class LR, Learner, Unrestricted
	 * @param select
	 */
	public static String buildSelect(String select) {
		if (select == null) {
			return XQueryTemplate.SELECT_TEMPLATE_FULL2;
		}
		List<String> colList = new ArrayList<String>();
		String[] cols = select.split(",");
		for (String string : cols) {
			colList.add(string.trim());
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < colList.size(); i++) {
			String newone = XQueryTemplate.SELECT_TEMPLATE2;
			newone = newone.replaceAll(XQueryTemplate.SELECT_REGEX, colList.get(i));
			buffer.append(newone);
		}
		String temlate = buffer.toString();
		return temlate;
	}
	
	/**
	 * e.g. where = quarter eq 2 and postcode eq 2000
	 * @param where
	 */
	public static String buildWhere(String where) {
		if (where == null) {
			return XQueryTemplate.EMPTY;
		}
		List<String> logicStrings = new ArrayList<String>();
		String pattern = "and|or";
		Pattern r = Pattern.compile(pattern);
		Matcher matcher = r.matcher(where);
		while(matcher.find()) {
			logicStrings.add(matcher.group().trim());
		}
		
		List<String> opStrings = new ArrayList<String>();
		String pattern2 = "eq|ne|gt|ge|lt|le|<[^=]|>[^=]|[^!<>]=|!=|<=|>=";
		Pattern r2 = Pattern.compile(pattern2);
		Matcher matcher2 = r2.matcher(where);
		while(matcher2.find()) {
			opStrings.add(matcher2.group().trim());
		}
		
		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		List<String> keyList = new ArrayList<String>();
		Map<String, String> map = null;
		String[] cols = where.split("and|or");
		for (String string : cols) {
			String temp = string.trim();
			String[] lr = temp.split("eq|ne|gt|ge|lt|le|<[^=]|>[^=]|[^!<>]=|!=|<=|>=");
			map = new HashMap<String, String>();
			map.put(lr[0].trim(), lr[1].trim());
			maplist.add(map);
			keyList.add(lr[0].trim());
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < maplist.size(); i++) {
			String newone = null;
			for (Entry<String, String> item : maplist.get(i).entrySet()) {
				newone = chooseWhereTemplate(item.getKey());
				newone = newone.replaceAll(XQueryTemplate.WHERE_LEFT_REGEX, item.getKey());
				newone = newone.replaceAll(XQueryTemplate.WHERE_RIGHT_REGEX, item.getValue());
				newone = newone.replaceAll(XQueryTemplate.WHERE_OP_REGEX, opStrings.get(i));
			}
			if (i == maplist.size() - 1) {
				buffer.append(newone);
				break;
			} else {
				buffer.append(newone + " " + logicStrings.get(i) + " ");
			}
		}
		return XQueryTemplate.generateWhereTemplate(buffer.toString());
	}
	
	/**
	 * e.g. orderby = Class C
	 * @param orderby
	 */
	public static String buildOrderby(String orderby) {
		if (orderby == null) {
			return "";
		}
		List<String> colList = new ArrayList<String>();
		String[] cols = orderby.split(",");
		for (String string : cols) {
			colList.add(string.trim());
		}
//		Collections.reverse(colList);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < colList.size(); i++) {
			String newone = chooseOrderByTemplate(colList.get(i));
			newone = newone.replaceAll(XQueryTemplate.ORDERBY_REGEX, colList.get(i));
			if (i == colList.size() - 1) {
				buffer.append(newone);
				break;
			} else {
				buffer.append(newone + ", ");
			}
		}
		return XQueryTemplate.generateOrderByTemplate(buffer.toString());
	}
	
	private static String chooseWhereTemplate(String str) {
		if ("quarter".equals(str) || "postcode".equals(str)) {
			return XQueryTemplate.WHERE_TEMPLATE_STR;
		} else{
			return XQueryTemplate.WHERE_TEMPLATE_INT;
		}
	}
	
	private static String chooseOrderByTemplate(String str) {
		if ("quarter".equals(str) || "postcode".equals(str)) {
			return XQueryTemplate.ORDERBY_TEMPLATE_STR;
		} else{
			return XQueryTemplate.ORDERBY_TEMPLATE_INT;
		}
	}
}
