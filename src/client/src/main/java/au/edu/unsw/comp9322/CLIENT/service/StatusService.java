package au.edu.unsw.comp9322.CLIENT.service;

import javax.servlet.http.HttpSession;

import au.edu.unsw.comp9322.CLIENT.bean.Status;

public interface StatusService {
	public Status updateStatus(HttpSession session);
}
