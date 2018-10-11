package au.edu.unsw.comp9322.CLIENT.bean;

public class MyNotice {
	private long id; 
	private long notice_id; 
	private String notice_uuid; 
	private long officer_id;
	private String status;
	
	public MyNotice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyNotice(long id, long notice_id, String notice_uuid, long officer_id, String status) {
		super();
		this.id = id;
		this.notice_id = notice_id;
		this.notice_uuid = notice_uuid;
		this.officer_id = officer_id;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(long notice_id) {
		this.notice_id = notice_id;
	}

	public String getNotice_uuid() {
		return notice_uuid;
	}

	public void setNotice_uuid(String notice_uuid) {
		this.notice_uuid = notice_uuid;
	}

	public long getOfficer_id() {
		return officer_id;
	}

	public void setOfficer_id(long officer_id) {
		this.officer_id = officer_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MyNotice [id=" + id + ", notice_id=" + notice_id + ", notice_uuid=" + notice_uuid + ", officer_id="
				+ officer_id + ", status=" + status + "]";
	}

	
	
}
