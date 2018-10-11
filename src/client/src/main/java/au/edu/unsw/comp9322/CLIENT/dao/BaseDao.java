package au.edu.unsw.comp9322.CLIENT.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.comp9322.CLIENT.constant.Constant;

public class BaseDao {
	public Connection getConnection() {
		String connectionURL="jdbc:mysql://localhost:"+Constant.MYSQL_PORT+"/"+Constant.MYSQL_DB_NAME+"?characterEncoding=utf-8";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(connectionURL, Constant.MYSQL_USERNAME, Constant.MYSQL_PASSWORD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public void closeConn(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean update(String sql, List<Object> list) {
		boolean isFlag = false;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					pstmt.setObject(i + 1, list.get(i));
				}
			}
			int num = pstmt.executeUpdate();
			if (num > 0) {
				System.out.println("true!!!!!!!");
				isFlag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(conn, pstmt, rs);
		}
		return isFlag;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> query(String sql, List<Object> list, Class clazz) {
		List<T> ls = new ArrayList<T>();
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					pstmt.setObject(i + 1, list.get(i));
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rm = rs.getMetaData();// 获得结果中存储的列名集合
			int num = rm.getColumnCount();
			while (rs.next()) {
				Object obj = clazz.newInstance();
				for (int i = 1; i <= num; i++) {
					String colName = rm.getColumnName(i);// 获取列名
					Object o = rs.getObject(colName);// 获取列值
					Field f = clazz.getDeclaredField(colName);// 获得指定的属性
					f.setAccessible(true);// 设置属性可被访问
					f.set(obj, o);// 给对象obejct的属性f赋值,值为o
				}
				ls.add((T) obj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(conn, pstmt, rs);
		}
		return ls;
	}
}
