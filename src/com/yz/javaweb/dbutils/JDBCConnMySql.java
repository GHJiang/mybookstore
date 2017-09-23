package com.yz.javaweb.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ��ȡ�������Ӻ��ͷ����ݿ����ӵ�һ�������ࡣ
 * 
 * @author Administrator
 *
 */
public class JDBCConnMySql {

	/**
	 * ��ȡ���ݿ�����ӡ�
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String jdbcUrl = "jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
			String userName = "root";
			String passWord = "";
			connection = DriverManager.getConnection(jdbcUrl, userName, passWord);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * �ͷ����ݿ�����ӡ�
	 * @param connection
	 */
	public static void relaseConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void relaseResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public static void relasePreparedStatement(PreparedStatement preparedStatement) {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

}
