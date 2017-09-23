package com.yz.javaweb.dbutils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yz.javaweb.web.ShopingCart;

public class BookStoreWebUtils {
	/**
	 * ��ȡ���ﳵ���� ��session��ȡ����û�иö����򴴽�һ���µĹ��ﳵ�����еĻ���ֱ�ӷ��ء�
	 * 
	 * @param request
	 * @return
	 */
	public static ShopingCart getShopingCart(HttpServletRequest request) {
		HttpSession session = request.getSession();

		ShopingCart sCart = (ShopingCart) session.getAttribute("ShopingCart");
		if (sCart == null) {
			sCart = new ShopingCart();
			session.setAttribute("ShopingCart", sCart);
		}
		return sCart;
	}
}
