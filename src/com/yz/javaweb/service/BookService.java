package com.yz.javaweb.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import com.yz.javaweb.dao.AccountDao;
import com.yz.javaweb.dao.BookDao;
import com.yz.javaweb.dao.TradeDao;
import com.yz.javaweb.dao.TradeItemDao;
import com.yz.javaweb.dao.UserDao;
import com.yz.javaweb.daoimpl.AccountDaoImpl;
import com.yz.javaweb.daoimpl.BookDaoImpl;
import com.yz.javaweb.daoimpl.TradeDaoImpl;
import com.yz.javaweb.daoimpl.TradeItemDaoImpl;
import com.yz.javaweb.daoimpl.UserDaoImpl;
import com.yz.javaweb.domain.Book;
import com.yz.javaweb.domain.ShoppingCartItem;
import com.yz.javaweb.domain.Trade;
import com.yz.javaweb.domain.TradeItem;
import com.yz.javaweb.web.CriteriaBook;
import com.yz.javaweb.web.Page;
import com.yz.javaweb.web.ShopingCart;

/**
 * ����һ��ֱ�ӵ���dao����м��ࡣ ����������������ܶ಻����dao���������顣����:ҵ�����
 * 
 * @author Administrator
 *
 */
public class BookService {

	private BookDao bookDao = new BookDaoImpl();
	private AccountDao accountDao = new AccountDaoImpl();
	private UserDao userDao = new UserDaoImpl();
	private TradeDao tradeDao = new TradeDaoImpl();
	private TradeItemDao tradeItemDao = new TradeItemDaoImpl();

	public Page<Book> getPage(CriteriaBook cBook) {
		return bookDao.getPage(cBook);
	}

	public Book getBook(int bookId) {

		return bookDao.getBook(bookId);
	}

	public boolean addToCart(int id, ShopingCart sCart) {
		Book book = bookDao.getBook(id);
		if (book != null) {
			sCart.addBook(book);
			return true;
		} else {
			return false;
		}
	}

	public void removeBookFromShopCart(ShopingCart shopingCart, int id) {
		shopingCart.removeItem(id);
	}

	public void clearBookFromShopCart(ShopingCart shopingCart) {
		shopingCart.clear();
	}

	public boolean isEmpty(ShopingCart shopingCart) {
		if (shopingCart.isEmpty()) {
			return true;
		}
		return false;
	}

	public void updateItemsQuantity(ShopingCart shopingCart, int id, int quantity) {
		shopingCart.updateItemQuantity(id, quantity);
	}

	public void cash(ShopingCart shopingCart, String userName, String accountId) {

		// 1.����book ���ݱ��е�saleAmount �� storeNumber��
		bookDao.batchUpdateStoreNumberAndSalesAmount(shopingCart.getItems());

		// 2.����account �˻��е���� balance��
		accountDao.updateBalance(Integer.parseInt(accountId), shopingCart.getTotalMoney());

		// 3.�� trade ���ݱ��в���һ����¼��
		Trade trade = new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userDao.getUser(userName).getUserId());
		tradeDao.insert(trade);

		// 4.�� tradeItem ���ݱ��в�����������¼��
		Collection<TradeItem> items = new ArrayList<>();
		for (ShoppingCartItem sCartItem : shopingCart.getItems()) {
			TradeItem tradeItem = new TradeItem();

			tradeItem.setBookId(sCartItem.getBook().getBookId());
			tradeItem.setQuantity(sCartItem.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());

			items.add(tradeItem);
		}
		tradeItemDao.batchSave(items);

		// 5.��չ��ﳵ��
		shopingCart.clear();

	}

}
