package com.yz.javaweb.dao;

import java.util.List;

/**
 * ʵ�ʲ����ķ��͵�Dao�ӿڡ�
 * @author Administrator
 *
 * @param <T>
 */
public interface Dao<T> {

	/**
	 * ִ�в������������ɹ��󣬷��ز����id.
	 * @param sql
	 * @param args
	 * @return
	 */
	long insert(String sql, Object... args);

	/**
	 * ִ�и��²�����
	 * @param sql
	 * @param args
	 */
	void update(String sql, Object... args);

	/**
	 * ִ�в�ѯ���������ز�ѯ���һ�����͵��ࡣ
	 * @param sql
	 * @param args
	 * @return
	 */
	T query(String sql, Object... args);

	/**
	 * ���в�ѯ���������ز�ѯ��Ķ����������һ�����ϡ�
	 * @param sql
	 * @param args
	 * @return
	 */
	List<T> queryForList(String sql, Object... args);

	/**
	 * ��ȡ��ĳһ���ֶ��ϵ�һ��ֵ��
	 * @param sql
	 * @param args
	 * @return
	 */
	<V> V getSingleVal(String sql, Object... args);

	/**
	 * �����������һ��������
	 * ����sql������������������
	 * @param sql
	 * @param args
	 */
	void batch(String sql, Object[]... args);

}
