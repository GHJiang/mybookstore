package com.yz.javaweb.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.yz.javaweb.domain.Book;
import com.yz.javaweb.domain.ShoppingCartItem;

public class ShopingCart {
	private Map<Integer, ShoppingCartItem> books = new HashMap<>();

	/**
	 * ���¹��ﳵ��ĳһ����������
	 * 
	 * @param id
	 * @param quantity
	 */
	public void updateItemQuantity(Integer id, int quantity) {
		ShoppingCartItem sCartItem = books.get(id);
		if (sCartItem != null) {
			sCartItem.setQuantity(quantity);
		}

	}

	/**
	 * �Ƴ�һ�����ﳵItem����ShoppingCartItem
	 * 
	 * @param id
	 */
	public void removeItem(Integer id) {
		books.remove(id);
	}

	/**
	 * ��չ��ﳵ��
	 */
	public void clear() {
		books.clear();
	}

	/**
	 * �жϹ��ﳵ�Ƿ�Ϊ�ա�
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return books.isEmpty();
	}

	/**
	 * ��ȡ���ﳵ�����е�����ܼۡ�
	 * 
	 * @return
	 */
	public float getTotalMoney() {
		float total = 0;

		for (ShoppingCartItem sCartItem : getItems()) {
			total += sCartItem.getItemMoney();
		}

		return total;
	}

	/**
	 * ��ȡ���е�book��������
	 * 
	 * @return
	 */
	public int getBookNumber() {
		int total = 0;
		for (ShoppingCartItem sCartItem : getItems()) {
			total += sCartItem.getQuantity();
		}

		return total;
	}

	/**
	 * ��ȡ���ﳵ�е�ÿһ��ShoppingCartItem��
	 * 
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems() {
		return books.values();
	}

	/**
	 * ��ȡ���е�ͼ����Ϣ��
	 * 
	 * @return
	 */
	public Map<Integer, ShoppingCartItem> getBooks() {
		return books;
	}

	/**
	 * �ж��Ƿ��и�bookid��ͼ�顣
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasBook(Integer id) {
		return books.containsKey(id);
	}

	/**
	 * ��map���������ͼ�顣
	 */
	public void addBook(Book book) {
		// 1.�жϸü������Ƿ��и�ͼ�顣
		ShoppingCartItem sCartItem = books.get(book.getBookId());
		if (sCartItem == null) {
			sCartItem = new ShoppingCartItem(book);
			books.put(book.getBookId(), sCartItem);
		} else {
			sCartItem.increment();
		}
	}
}
