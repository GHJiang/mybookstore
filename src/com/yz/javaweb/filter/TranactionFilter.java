package com.yz.javaweb.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yz.javaweb.dbutils.JDBCConnMySql;
import com.yz.javaweb.web.ConnectionContext;

/**
 * Servlet Filter implementation class TranactionFilter
 */
@WebFilter("/*")
public class TranactionFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public TranactionFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Connection connection = null;

		try {
			// 1.��ȡ���ӡ�
			connection = JDBCConnMySql.getConnection();

			// 2.��������
			connection.setAutoCommit(false);

			// 3.����ThreadLocal�������ӡ�
			ConnectionContext.getInstance().bind(connection);

			// 4.�ύ��Servlet��
			chain.doFilter(request, response);

			// 5.�ύ����
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				// �ع�����
				connection.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
		} finally {

			// 6.ȡ���󶨵����ӡ�
			ConnectionContext.getInstance().remove();

			// 7.ȡ�����ӡ�
			JDBCConnMySql.relaseConnection(connection);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
