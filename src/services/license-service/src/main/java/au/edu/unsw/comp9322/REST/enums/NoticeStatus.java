package au.edu.unsw.comp9322.REST.enums;

public enum NoticeStatus {
	OFFICER_INITIAL(1), DRIVER_INITIAL(2);

	int code;

	private NoticeStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public static NoticeStatus fromCode(int code) {
		for (NoticeStatus type : NoticeStatus.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		return null;
	}
}
