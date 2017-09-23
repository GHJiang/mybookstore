package com.yz.javaweb.dao;

import java.util.Collection;
import java.util.Set;

import com.yz.javaweb.domain.TradeItem;

public interface TradeItemDao {

	
	/**
	 * �������� Collection<TradeItem> �ļ��ϡ�
	 * @param items
	 */
	public abstract void batchSave(Collection<TradeItem> items);
	
	/**
	 * ���� tradeId ��ȡ��  TradeItems �� Set ���ϡ�
	 * @param tradeId
	 * @return
	 */
	public abstract Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId);
	
	
}
