package com.yz.javaweb.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.yz.javaweb.dbutils.BookStoreWebUtils;
import com.yz.javaweb.domain.Account;
import com.yz.javaweb.domain.Book;
import com.yz.javaweb.domain.ShoppingCartItem;
import com.yz.javaweb.domain.User;
import com.yz.javaweb.service.AccountService;
import com.yz.javaweb.service.BookService;
import com.yz.javaweb.service.UserService;
import com.yz.javaweb.web.CriteriaBook;
import com.yz.javaweb.web.Page;
import com.yz.javaweb.web.ShopingCart;

public class GetBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡ�����ø�GetBooksServlet����һ����������
		String methodName = request.getParameter("method");
		try {
			// ͨ�����䣬���ݷ��������ø÷�����
			Method method = getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private BookService bookService = new BookService();

	public void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1; // ����ҳ��ĳ�ʼֵΪ1.
		int minPrice = 0; // Ĭ����ͼ۸�Ϊ0Ԫ.
		int maxPrice = Integer.MAX_VALUE; // Ĭ�����۸�Ϊ�����.

		String pageNoStr = request.getParameter("pageNo"); // ��ȡ��ҳ�洫����pageNo��ֵ���ַ������͡�
		String minPriceStr = request.getParameter("minPrice");// ��ȡ��ҳ�洫����minPrice��ֵ���ַ������͡�
		String maxPriceStr = request.getParameter("maxPrice");// ��ȡ��ҳ�洫����maxPrice��ֵ���ַ������͡�

		try {
			pageNo = Integer.parseInt(pageNoStr); // �ַ���ת��Ϊ�������͡�
		} catch (NumberFormatException e) {
		}
		try {
			minPrice = Integer.parseInt(minPriceStr); // �ַ���ת��Ϊ�������͡�
		} catch (NumberFormatException e) {
		}
		try {
			maxPrice = Integer.parseInt(maxPriceStr); // �ַ���ת��Ϊ�������͡�
		} catch (NumberFormatException e) {
		}
		// ����CriteriaBook������pageNo��minPrice��maxPrice����ֵ�����CriteriaBook�������ڴ������Ĳ�ѯ����װΪһ�������ˡ�
		CriteriaBook cb = new CriteriaBook(minPrice, maxPrice, pageNo);
		// ���ݷ�װ�õĲ�ѯ��������ȡ����һҳ����bookService��һ���м���ת��һ���࣬����CriteriaBook��ȡ����ҳ��
		Page<Book> page = bookService.getPage(cb);
		// ��ȡ����ҳ��page�ͻ�ȡ���ˣ���ҳ��pageNo��list<book>
		// �ѻ�ȡ����page��ŵ�setAttribute�С�
		request.setAttribute("bookpage", page);
		// ת����books.jsp
		request.getRequestDispatcher("/books.jsp").forward(request, response);
	}

	public void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ��ȡ���û������bookid��
		String idStr = request.getParameter("bookId");
		// ��ʼ��bookId������һ��Ĭ�ϵ�ֵ��-1����ʾ�����ڡ�
		int bookId = -1;
		Book book = null;
		try {
			bookId = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		if (bookId > 0) {
			// �����ݿ��л�ȡ�����顣bookService��ײ�dao�����������ݿ����õ�book��
			book = bookService.getBook(bookId);
		}
		// �ж�book�Ƿ�Ϊ�ա�
		if (book == null) {
			// ��bookΪ�գ���ת��error.jsp����ҳ�档������
			response.sendRedirect(request.getContextPath() + "/error.jsp");
			return;
		}
		// ��Ϊ�գ��洢��request�У�ת����ҳ����ת��
		request.setAttribute("book", book);
		request.getRequestDispatcher("/book.jsp").forward(request, response);
	}

	public void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.��ȡ��book��id��
		String idString = request.getParameter("bookId");

		int id = -1;
		boolean flag = false;

		try {
			id = Integer.parseInt(idString);
		} catch (NumberFormatException e) {
		}

		if (id > 0) {
			// 2.��ȡ�����ﳵ����
			ShopingCart shopingCart = BookStoreWebUtils.getShopingCart(request);
			// 3.����bookservice��addToCart��������Ʒ���뵽���ﳵ�С�
			flag = bookService.addToCart(id, shopingCart);
		}

		if (flag) {
			// 4.ֱ�ӵ���getBooks������
			getBooks(request, response);
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}

	}

	public void forwordPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/" + page + ".jsp").forward(request, response);
	}

	private UserService userService = new UserService();

	public void cashBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String accountId = request.getParameter("accountId");
		StringBuffer error = voilteByFiled(userName, accountId);
		// �жϼ���֤�������Ƿ�Ϊ�յ���֤��
		if (error.toString().equals("")) {
			// �ж��û������˺��Ƿ�ƥ�䡣
			error = voilteUser(userName, accountId);
			if (error.toString().equals("")) {
				// �жϿ���Ƿ���㡣
				error = voilteBookStoreNumber(request);
				if (error.toString().equals("")) {
					// �ж�����Ƿ���㡣
					error = voilteBalance(request, accountId);
				}
			}
		}
		if (!error.toString().equals("")) {
			request.setAttribute("error", error);
			request.getRequestDispatcher("/cash.jsp").forward(request, response);
			return;
		}
		// ���е���֤���֮�󣬿�ʼ���˲�����
		bookService.cash(BookStoreWebUtils.getShopingCart(request), userName, accountId);
		response.sendRedirect(request.getContextPath() + "/success.jsp");
	}

	/**
	 * �ж�����Ƿ���㡣
	 * 
	 * @param accountId
	 * @return
	 */

	private AccountService accountService = new AccountService();

	public StringBuffer voilteBalance(HttpServletRequest request, String accountId) {
		StringBuffer error = new StringBuffer("");
		Account account = accountService.getAccount(Integer.parseInt(accountId));
		Integer balance = account.getBalance();
		ShopingCart shopingCart = BookStoreWebUtils.getShopingCart(request);
		float totalMoney = shopingCart.getTotalMoney();
		if (totalMoney > balance) {
			error.append("�˻�����!");
		}
		return error;
	}

	/**
	 * �жϿ���Ƿ���㡣
	 * 
	 * @param request
	 * @return
	 */
	public StringBuffer voilteBookStoreNumber(HttpServletRequest request) {
		StringBuffer error = new StringBuffer("");
		ShopingCart shopingCart = BookStoreWebUtils.getShopingCart(request);
		// ��ȡ�����ﳵ�����е�item�������е�BookId��Ӧ��ShoppingCartItem bookId : bookQuantity.
		Map<Integer, ShoppingCartItem> books = shopingCart.getBooks();
		Set<Integer> bookIds = books.keySet();
		for (Integer bookId : bookIds) {
			Book book = bookService.getBook(bookId);
			// ��ȡ���̵���ʣ������BookId��
			int storeNumber = book.getStoreNumber();
			// ��ȡ���û���ĸ����������
			int getNumber = books.get(bookId).getQuantity();
			if (storeNumber < getNumber) {
				error.append(book.getTitle() + "��治��!<br>");
			}
		}
		return error;
	}

	/**
	 * �ж��û������˺��Ƿ�ƥ�䡣
	 * 
	 * @param userName
	 * @param accountId
	 * @return
	 */
	public StringBuffer voilteUser(String userName, String accountId) {
		User user = userService.getUser(userName);
		boolean flag = false;
		if (user != null) {
			Integer accountId2 = user.getAccountId();
			if (accountId != null && accountId.trim().equals("" + accountId2)) {
				flag = true;
			}
		}
		StringBuffer error2 = new StringBuffer("");
		if (!flag) {
			error2.append("�û������˺Ų�ƥ��!");
		}
		return error2;
	}

	/**
	 * �жϱ�������ı��� ���Ƿ�Ϊ�ա�
	 * 
	 * @param userName
	 * @param accountId
	 * @return
	 */
	public StringBuffer voilteByFiled(String userName, String accountId) {
		StringBuffer error = new StringBuffer("");
		if (userName == null || userName.trim().equals("")) {
			error.append("�û���Ϊ��!<br>");
		}
		if (accountId == null || accountId.trim().equals("")) {
			error.append("�˺�Ϊ��!");
		}
		return error;
	}

	public void removeBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("bookId");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		ShopingCart shopingCart = BookStoreWebUtils.getShopingCart(request);
		bookService.removeBookFromShopCart(shopingCart, id);
		if (bookService.isEmpty(shopingCart)) {
			request.getRequestDispatcher("/emptycart.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/shopcart.jsp").forward(request, response);
	}

	public void clearBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ShopingCart shopingCart = BookStoreWebUtils.getShopingCart(request);
		bookService.clearBookFromShopCart(shopingCart);
		request.getRequestDispatcher("/emptycart.jsp").forward(request, response);
	}

	public void updateItemsQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		ShopingCart shopingCart = BookStoreWebUtils.getShopingCart(request);
		int id = -1;
		int quantity = -1;
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (NumberFormatException e) {
		}
		if (id > 0 && quantity > 0) {
			bookService.updateItemsQuantity(shopingCart, id, quantity);
		}

		// ����JSON���ݡ�
		Map<String, Object> result = new HashMap<>();
		result.put("bookNumber", shopingCart.getBookNumber());
		result.put("totalMoney", shopingCart.getTotalMoney());

		Gson gson = new Gson();
		String reStr = gson.toJson(result);

		response.setContentType("text/javascript");
		response.getWriter().print(reStr);

	}

}
