package com.yz.javaweb.dao;

import com.yz.javaweb.domain.User;

public interface UserDao {

	/**
	 * �����û�������ȡ�� User ����
	 * @param userName
	 * @return
	 */
	public abstract User getUser(String userName);
	
}
