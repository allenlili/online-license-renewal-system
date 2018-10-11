package au.edu.unsw.comp9322.CLIENT.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import au.edu.unsw.comp9322.CLIENT.bean.MyOfficer;

@Component
public class OfficerDao {

	BaseDao baseDao = new BaseDao();
	List<Object> attribute_list;	
	String sql;
	
	public MyOfficer getOfficer(int id) {
		attribute_list=new ArrayList<Object>();
		sql="select * from officer_info where id = (?)";
		attribute_list.add(id);
		
		List<MyOfficer> result_list = new ArrayList<MyOfficer>();
		result_list=baseDao.query(sql,attribute_list,MyOfficer.class);
		if (result_list.size()>0)
			return result_list.get(0);
		else return null;
	}
	
	public MyOfficer getOfficerByNamePassword(String name, String password) {
		attribute_list=new ArrayList<Object>();
		sql="select * from officer_info where name = (?) and password = (?)";	
		attribute_list.add(name);
		attribute_list.add(password);
		
		List<MyOfficer> result_list = new ArrayList<MyOfficer>();
		result_list=baseDao.query(sql,attribute_list,MyOfficer.class);
		if (result_list.size()>0)
			return result_list.get(0);
		else return null;
	}
	
	
//	public static void main(String[] args) {
//		OfficerDao officerDao = new OfficerDao();
//		officerDao.getOfficer(0);
//		officerDao.getOfficerByNamePassword("selina", "123456");
//	}
}
