package com.infinova.lucene.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.infinova.lucene.bean.PageBean;
import com.infinova.lucene.bean.Product;
import com.infinova.lucene.bean.ResultBean;
/**
 * 商品dao层
 * @author yizl
 *
 */
public class ProductDao {

	private static String url = "jdbc:mysql://localhost:3306/lucene?useUnicode=true&characterEncoding=utf8";
	private static String user = "root";
	private static String password = "root";
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		// 通过工具类获取连接对象
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	/**
	 * 批量增加商品
	 * @param pList
	 */
	public void insertProduct(List<Product> pList) {
		String insertProductTop="INSERT INTO `product` (`id`, `name`, "
				+ "`category`, `price`, `place`, `code`) VALUES ";
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			// 3.创建Statement对象
			stmt = conn.createStatement();
			int count=0;
			// 4.sql语句
			StringBuffer sb = new StringBuffer();
			for (int i = 0,len=pList.size(); i < len; i++) {
				Product product = pList.get(i);
				sb.append("(" + product.getId() + ",'" + product.getName() 
				+ "','" + product.getCategory()+ "'," + product.getPrice()
				+ ",'" + product.getPlace() + "','" + product.getCode() + "')");
				if (i==len-1) {
					sb.append(";");
					break;
				}else {
					sb.append(",");
				}
				//数据量太大会导致一次执行不了,一次最多执行20000条
				if(i%20000==0&&i!=0) {
					sb.deleteCharAt(sb.length()-1);
					sb.append(";");
					String sql = insertProductTop+sb;
					count += stmt.executeUpdate(sql);
					//将sb清空
					sb.delete(0, sb.length());
				}
			}
			
			String sql = insertProductTop+sb;
			// 5.执行sql
			count += stmt.executeUpdate(sql);
			System.out.println("影响了" + count + "行");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(conn, stmt);
		}
	}
	
	/**
	 * 关闭资源
	 * @param conn
	 * @param stmt
	 */
	private void close(Connection conn, Statement stmt) {
		// 关闭资源
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	// 
	public void deleteAllProduct() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			// 3.创建Statement对象
			stmt = conn.createStatement();
			// 4.sql语句
			String sql = "delete from product";
			// 5.执行sql
			int count = stmt.executeUpdate(sql);
			System.out.println("影响了" + count + "行");

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			close(conn, stmt);
		}
	}

	/**
	 * 查询所有商品
	 */
	public List<Product> selectAllProduct() {
		List<Product> pList=new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			// 3.创建Statement对象
			stmt = conn.createStatement();
			// 4.sql语句
			String sql = "select * from product";
			// 5.执行sql
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Product product=new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPlace(rs.getString("place"));
				product.setPrice(rs.getFloat("price"));
				product.setCode(rs.getString("code"));
				pList.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			close(conn, stmt);
		}
		return pList;
	}
	/**
	 * 通过商品名模糊匹配商品
	 * @param strName
	 * @param pageNow
	 * @param pageSize
	 * @return
	 */
	public ResultBean<List<Product>> selectProductOfName(String strName, int pageNow, int pageSize) {
		ResultBean<List<Product>> resultBean=new ResultBean<List<Product>>();
		PageBean pageBean =new PageBean();
		pageBean.setPageNow(pageNow);
		pageBean.setPageSize(pageSize);
		List<Product> pList=new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			// sql语句
			String sql = "SELECT id,name,category,place,price,code FROM product"
	                + " where name like ? limit "+(pageNow-1)*pageSize+","+pageSize; 
			// 3.创建PreparedStatement对象,sql预编译
			pstmt = conn.prepareStatement(sql);
			// 4.设定参数
			pstmt.setString(1, "%" + strName + "%" );                  
			// 5.执行sql,获取查询的结果集  
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product=new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPlace(rs.getString("place"));
				product.setPrice(rs.getFloat("price"));
				product.setCode(rs.getString("code"));
				pList.add(product);
			}
			
			String selectCount = "SELECT count(1) c FROM product"
	                + " where name like ? ";
			pstmt = conn.prepareStatement(selectCount);
			pstmt.setString(1, "%" + strName + "%" ); 
			ResultSet rs1 = pstmt.executeQuery();
			int count=0;
			while (rs1.next()) {
				count = rs1.getInt("c");
			}
			pageBean.setTotal(count);
			resultBean.setPageBean(pageBean);
			resultBean.setData(pList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			// 关闭资源
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		return resultBean;
	
	}

}
