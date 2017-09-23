package com.yz.javaweb.dao;

import java.util.Collection;
import java.util.List;

import com.yz.javaweb.domain.Book;
import com.yz.javaweb.domain.ShoppingCartItem;
import com.yz.javaweb.web.CriteriaBook;
import com.yz.javaweb.web.Page;

/**
 * 
 * @author Administrator
 *
 */
public interface BookDao {

	/**
	 * ����ָ����id����book����
	 * @param id
	 * @return
	 */
	public abstract Book getBook(int id);
	/**
	 * ���ݴ���� CriteriaBook ���󷵻ض�Ӧ��Page ����
	 * @param cb
	 * @return
	 */
	public abstract Page<Book> getPage(CriteriaBook cb);
	
	/**
	 * ���ݴ���� CriteriaBook ���󷵻����Ӧ�ļ�¼����
	 * @param cb
	 * @return
	 */
	public abstract long getTotalBookNumber(CriteriaBook cb);
	
	/**
	 * ���ݴ���� CriteriaBook �� pageSize ���ض�Ӧ�� List<Book> �ļ��ϡ�
	 * @param cb
	 * @param pageSize
	 * @return
	 */
	public abstract List<Book> getPageBook(CriteriaBook cb, int pageSize);
	
	/**
	 * ����ָ����id ��book ��StoreNumber�ֶε�ֵ��
	 * @param id
	 * @return
	 */
	public abstract int getStoreNumber(Integer id);
	
	/**
	 * ���ݴ����Collection<ShoppingCartItem> �ļ��ϡ�
	 * �������� book ���ݱ�� storenumber �� salesnumber�ֶε�ֵ��
	 * @param items
	 */
	public abstract void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items);
}
