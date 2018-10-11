package au.edu.unsw.comp9322.CLIENT.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import au.edu.unsw.comp9322.CLIENT.bean.MyNotice;

@Component
public class NoticeDao {
	BaseDao baseDao = new BaseDao();
	List<Object> attribute_list;	
	String sql;
	
	public MyNotice getNoticeById(int id) {
		attribute_list=new ArrayList<Object>();
		sql="select * from notice_info where id = (?)";
		attribute_list.add(id);
		
		List<MyNotice> result_list = new ArrayList<MyNotice>();
		result_list=baseDao.query(sql,attribute_list,MyNotice.class);
		if (result_list.size()>0)
			return result_list.get(0);
		else return null;
	}
	
	public boolean insertNotice(long notice_id,UUID notice_uuid, long officer_id,String status) {
		attribute_list=new ArrayList<Object>();
		sql="insert into notice_info(notice_id,notice_uuid,officer_id,status) values(?,?,?,?)";	
		System.out.println(notice_uuid);
		System.out.println(notice_uuid.toString().replaceAll("-", ""));
		
		attribute_list.add(notice_id);
		attribute_list.add(notice_uuid.toString().replaceAll("-", ""));
		attribute_list.add(officer_id);
		attribute_list.add(status);
		
		return baseDao.update(sql,attribute_list);
	}
	
	public List<MyNotice> getPendingManualValidate(long id) {
		attribute_list=new ArrayList<Object>();
		sql="select * from notice_info where officer_id = (?) and status=\"manual_validate\";";
		attribute_list.add(id);
		
		List<MyNotice> result_list = new ArrayList<MyNotice>();
		result_list=baseDao.query(sql,attribute_list,MyNotice.class);
		if (result_list.size()>0)
			return result_list;
		else return null;
	}
	
	public List<MyNotice> getPendingManualExtend(long id) {
		attribute_list=new ArrayList<Object>();
		sql="select * from notice_info where officer_id = (?) and status=\"manual_extend\";";
		attribute_list.add(id);
		
		List<MyNotice> result_list = new ArrayList<MyNotice>();
		result_list=baseDao.query(sql,attribute_list,MyNotice.class);
		if (result_list.size()>0)
			return result_list;
		else return null;
	}

	public Boolean sendStatus(long id, String status) {
		System.out.println("id "+id+" set status "+status);
		attribute_list=new ArrayList<Object>();
		sql="update notice_info set status = (?) where notice_id = (?);";	
		
		attribute_list.add(status);
		attribute_list.add(id);
		
		return baseDao.update(sql,attribute_list);
		
	}
}
