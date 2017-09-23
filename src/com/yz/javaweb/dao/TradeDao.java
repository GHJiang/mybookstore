package com.yz.javaweb.dao;

import java.util.Set;

import com.yz.javaweb.domain.Trade;

public interface TradeDao {

	/**
	 * ���ݲ����������ݿ��в���һ�����׼�¼��
	 * @param trade
	 */
	public abstract void insert(Trade trade);
	
	/**
	 * ���ݴ���� userId ����ȡ�� ����������� Set<Trade> �ļ��ϡ�
	 * @param userId
	 * @return
	 */
	public abstract Set<Trade> getTradesWithUserId(Integer userId);
	
}
