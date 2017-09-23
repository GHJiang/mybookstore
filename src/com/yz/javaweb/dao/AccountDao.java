package com.yz.javaweb.dao;

import com.yz.javaweb.domain.Account;

public interface AccountDao {

	/**
	 * ���� accountId ����ȡ��һ��Account �Ķ���
	 * 
	 * @param accountId
	 * @return
	 */
	public abstract Account get(Integer accountId);

	/**
	 * ���ݴ���� accountId ��amount ����ָ���˻������۳� ָ���� amount ��Ǯ����
	 * 
	 * @param accountId
	 * @param amount
	 */
	public abstract void updateBalance(Integer accountId, float amount);

}
